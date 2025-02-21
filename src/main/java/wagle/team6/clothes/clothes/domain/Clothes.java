package wagle.team6.clothes.clothes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import wagle.team6.clothes.member.domain.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id")
    @JsonIgnore
    private Category category;

    @OneToOne(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setMember(Member member) {
        if(this.member != null)
            member.getClothesList().remove(this);
        this.member = member;
        member.getClothesList().add(this);
    }

    public void setCategory(Category category) {
        if(this.category != null)
            category.getClothesList().remove(this);
        this.category = category;
        category.getClothesList().add(this);
    }

    public void setImage(Image image) {
        this.image = image;
        image.setClothes(this); // 양방향 관계 설정
    }
}
