package wagle.team6.clothes.clothes.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_key", nullable = false)
    private Long categoryKey;

    @Column(name = "category_key2")
    private Long categoryKey2; // 상위 카테고리 ID (Self-reference)

    @Column(nullable = false, length = 40)
    private String name;
}
