package wagle.team6.clothes.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wagle.team6.clothes.ai.dto.Request.ChattingRequest;
import wagle.team6.clothes.ai.service.AiService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    /* 채팅 API */
    @PostMapping("/chats")
    public ResponseEntity<String> chat(@RequestBody ChattingRequest chattingRequest){

        String aiResponse = aiService.chat(chattingRequest);

        return ResponseEntity.ok(aiResponse);
    }

    /* 추천 API */
    @PostMapping("/recommends")
    public ResponseEntity<String> recommend(){

        return null;
    }




}
