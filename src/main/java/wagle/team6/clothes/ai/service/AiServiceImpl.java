package wagle.team6.clothes.ai.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import wagle.team6.clothes.ai.dto.Request.ChattingRequest;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    private final ChatClient chatClient;
    private final RedisTemplate<String, String> redisTemplate;


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

        // 이전 메시지들이 없다면 빈 문자열 반환
//        if (messages == null || messages.isEmpty()) {
//            return "";
//        }

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


}
