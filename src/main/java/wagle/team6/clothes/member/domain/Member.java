package wagle.team6.clothes.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // 기본 생성자 추가 (JPA 사용을 위해)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member registerMember(
            String email, String password) {
        return new Member(email, password);
    }


}
