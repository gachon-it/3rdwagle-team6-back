package wagle.team6.clothes.ai.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import wagle.team6.clothes.ai.dto.Request.ChattingRequest;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    private final ChatClient chatClient;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 채팅 메서드
     * @param chattingRequest
     * @return
     */
    @Override
    public String chat(ChattingRequest chattingRequest) {

        /* 채팅 로직 */
        String message = chattingRequest.message();
        Long id = chattingRequest.userId();

        /* 레디스에서 이전 메시지 조회 */
        String previousMessage  = getPreviousMessage(id);
        log.info(previousMessage);

        log.info(message);
        /* AI에게 보낼 메시지 프롬프팅 */
        String promptMessgae = makePromptMessage(message, previousMessage);


        /* 응답 메시지 반환 */
        String aiResponse = chatClient.call(promptMessgae);
        log.info(aiResponse);

        /* 사용자 아이디 + 사용자 메시지 + 응답 메시지 Redis에 저장 */
        saveMessageInRedis(id,message,aiResponse);

        return aiResponse;
    }

    /* 레디스에서 기존 메시지 반환 메서드 */
    private String getPreviousMessage(Long id){

        String key = id + ":chat";  // 사용자 아이디와 연결된 Redis 키 생성
        // 리스트에서 마지막 10개의 메시지를 조회 (예: 0부터 9까지 10개)
        List<String> messages = redisTemplate.opsForList().range(key, 0, 9);

        // 메시지를 하나의 문자열로 결합하여 반환
        return String.join(" ", messages);
    }

    /* 메시지 프롬프팅 메서드 */
    private String makePromptMessage(String message,String previousMessage){

        return "당신은 옷을 추천해주는 AI 상담사다. 당신이 이전에 사용자와 나눈 내용은 다음과 같다"
                + previousMessage
                + "이를 참고하여 사용자의 다음 메시지를 보고 적절히 답변하라."
                + message;
    }

    /* Redis에서 사용자와 챗봇의 메시지 저장*/
    public void saveMessageInRedis(Long id,String userMessage ,String aiResponse){
        // Redis에 사용자와 챗봇의 메시지 저장
        saveMessage(id, userMessage, "user");
        saveMessage(id, aiResponse, "chatbot");
    }

    /* Redis에 메시지 저장 형식 정의 메서드*/
    private void saveMessage(Long id, String message, String sender) {
        String key = id + ":chat" ;
        String taggedMessage = sender + ": " + message;
        redisTemplate.opsForList().rightPush(key, taggedMessage);
    }

////////////////////////////////////////////////////////////////////////////////////

    /**
     * 의상 추천 메서드
     * @param userId
     * @return
     */
    @Override
    public String recommend(Long userId) {

        /* 모든 메시지 조회 */
        String everyMessages = getAllMessage(userId);
        log.info(everyMessages);

        /* 요청 프롬프트 메시지 */
        String promptMessage = recommendPromptMessage(everyMessages);

        /* 의상 추천 */
        String topColorResponse = chatClient.call(promptMessage);

        /* 의상 색 정제 */
        String topColor = extractColor(topColorResponse);

        // Redis의 모든 메시지 삭제
        deleteAllMessages(userId);

        return topColor;
    }

    /* 레디스에서 모든 메시지 반환 메서드 */
    private String getAllMessage(Long id){

        String key = id + ":chat";
        List<String> messages = redisTemplate.opsForList().range(key, 0, 9);

        // 메시지를 하나의 문자열로 결합하여 반환
        return String.join(" ", messages);
    }


    /* 레디스에서 모든 메시지 삭제 */
    private void deleteAllMessages(Long id) {
        String key = id + ":chat";
        redisTemplate.delete(key);
        log.info("Redis에서 메시지 삭제 완료: {}", key);
    }

    /* AI 추천 메서드에서의 프롬프트 메시지 */
    private String recommendPromptMessage(String everyMessages){

        return "다음 너는 AI 옷추천 상담사이다."
                + "다음 대화는 너와 사용자의 대화 내용인데, 이 대화를 보고 이날 사용자가 입고 가면 좋을 상의 옷 색 하나만 추천해줘."
                + " 색깔은 다음 색깔 중에서 하나를 선정해줘. "
                + "{흰색,베이지색,회색,분홍색,노란색,검은색,갈색,파란색,초록색,빨간색}"
                + "다음은 너와 사용자의 대화내용이다. -> "
                + everyMessages;
    }


    // 색 목록
    private static final List<String> COLORS = Arrays.asList("흰색", "베이지색", "회색", "분홍색",
            "노란색", "검은색", "갈색", "파란색",
            "초록색", "빨간색");

    public static String extractColor(String input) {
        // 문장에서 색을 찾아서 반환
        for (String color : COLORS) {
            if (input.contains(color)) {
                return color;
            }
        }
        // 색이 없으면 null 또는 다른 기본값 반환
        return "흰색";
    }

////////////////////////////////////////////////////////////////////

    /**
     * 의상 매칭 메서드
     * @param topColor
     * @return
     */
    @Override
    public String match(String topColor) {

        /* 상의 색상별 추천 하의 색상 매핑 */
        Map<String, String> outfitMap = new HashMap<>();
        outfitMap.put("흰색", "검은색");
        outfitMap.put("베이지색", "검은색");
        outfitMap.put("회색", "갈색");
        outfitMap.put("분홍색", "흰색");
        outfitMap.put("노란색", "갈색");
        outfitMap.put("검은색", "베이지색");
        outfitMap.put("갈색", "흰색");
        outfitMap.put("파란색", "흰색");
        outfitMap.put("초록색", "흰색");
        outfitMap.put("빨간색", "흰색");

        /* 상의 색에 맞는 하의 색을 가져옴 */
        String bottomColor = outfitMap.get(topColor);
        log.info("추천하는 하의 색 : {}" ,outfitMap.get(topColor));

        return bottomColor;
    }

////////////////////////////////////////////////////////////////////////////


}
