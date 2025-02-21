package wagle.team6.clothes.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wagle.team6.clothes.clothes.domain.Category;
import wagle.team6.clothes.clothes.domain.Clothes;

import java.util.List;
import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {
    @Query("SELECT c FROM Clothes c " +
            "INNER JOIN c.category cat " +
            "WHERE c.member.id = :memberId " +
            "AND cat.categoryKey = :categoryKey")
    List<Clothes> findClothesByMemberAndCategory(@Param("memberId") Long memberId,
                                                 @Param("categoryKey") Long categoryKey);

    @Query("SELECT c FROM Clothes c " +
            "INNER JOIN c.category cat " +
            "INNER JOIN c.member mem " +
            "WHERE mem.id = :memberId " +
            "AND cat.categoryKey = :categoryKey1 " +
            "AND cat.categoryKey2 = :categoryKey2")
    List<Clothes> findClothesByMemberAndCategoryKeys(
            @Param("memberId") Long memberId,
            @Param("categoryKey1") Long categoryKey1,
            @Param("categoryKey2") Long categoryKey2);


}
