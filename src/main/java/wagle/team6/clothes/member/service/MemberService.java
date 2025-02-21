package wagle.team6.clothes.member.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wagle.team6.clothes.member.domain.Member;
import wagle.team6.clothes.member.dto.MemberRequestDTO;
import wagle.team6.clothes.member.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Transactional
    public Long signUp(MemberRequestDTO memberDto) {
        Member member = new Member(memberDto.getEmail(), memberDto.getPassword());
        Member saveMember = memberRepository.save(member);
        System.out.println("계정: " + saveMember.getId());
        return saveMember.getId();
    }

    @Transactional
    public Member login(MemberRequestDTO memberDto){
        Optional<Member> member = memberRepository.findByEmailAndPassword(memberDto.getEmail(),memberDto.getPassword());
        return member.get();
    }
}
