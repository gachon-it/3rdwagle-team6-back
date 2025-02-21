package wagle.team6.clothes.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagle.team6.clothes.ai.dto.Request.ChattingRequest;
import wagle.team6.clothes.ai.dto.Response.ClothesResponse;
import wagle.team6.clothes.ai.service.AiService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    /**
     * 채팅 API
     * @param chattingRequest
     * @return
     */
    @PostMapping("/chats")
    public ResponseEntity<String> chat(@RequestBody ChattingRequest chattingRequest){

        /* 채팅 메시지 반환 */
        String aiResponse = aiService.chat(chattingRequest);

        return ResponseEntity.ok(aiResponse);
    }

    /**
     * 추천 API
     * @param userId
     * @return
     */
    @PostMapping("/recommends/{userId}")
    public ResponseEntity<ClothesResponse> recommend(@PathVariable("userId") Long userId){

        /* 채팅을 기반으로 상의 추천 */
        String topColor = aiService.recommend(userId);

        /* 상의 색 추천 */
        log.info("recommend top color: {}", topColor);

        /* 색이 매칭 되는 하의 추천 */
        String bottomColor = aiService.match(topColor);
        log.info("recommend bottom color: {}", bottomColor);

        /* ClothesResponse 객체 생성 */
        ClothesResponse clothesResponse = new ClothesResponse(topColor, bottomColor);

        return ResponseEntity.ok(clothesResponse);
    }

}
