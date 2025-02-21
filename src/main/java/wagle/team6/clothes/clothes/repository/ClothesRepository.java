package wagle.team6.clothes.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wagle.team6.clothes.clothes.domain.Clothes;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

}
