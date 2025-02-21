package wagle.team6.clothes.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wagle.team6.clothes.member.domain.Member;
import wagle.team6.clothes.member.dto.MemberRequestDTO;
import wagle.team6.clothes.member.service.MemberService;

@Controller
@RestController
@RequestMapping("/member")
public class MemberController {


    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    public Long signUp(@RequestBody MemberRequestDTO request) {
        return memberService.signUp(request);
    }


    @PostMapping("/login")
    public Member login(@RequestBody MemberRequestDTO request) {
        return memberService.login(request);
    }

}
