package wagle.team6.clothes.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wagle.team6.clothes.clothes.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryKeyAndCategoryKey2(Long categoryKey, Long categoryKey2);
}
