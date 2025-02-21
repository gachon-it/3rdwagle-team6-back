package wagle.team6.clothes.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wagle.team6.clothes.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
