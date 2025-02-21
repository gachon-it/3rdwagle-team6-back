package wagle.team6.clothes.ai.service;

import wagle.team6.clothes.ai.dto.Request.ChattingRequest;
import wagle.team6.clothes.ai.dto.Response.ClothesResponse;

public interface AiService {

    /* 채팅 메서드 */
    String chat(ChattingRequest chattingRequest);

    /* 의상 추천 메서드 */
    String recommend(Long userId);

    /* 의상 매칭 메서드 */
    String match(String topColor);


}
