package wagle.team6.clothes.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wagle.team6.clothes.clothes.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
