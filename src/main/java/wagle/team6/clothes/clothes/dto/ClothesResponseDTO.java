package wagle.team6.clothes.clothes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagle.team6.clothes.clothes.domain.Clothes;

import java.time.LocalDateTime;

public class ClothesResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateClothesResultDTO {
        Long id;
        LocalDateTime createdAt;
    }

    public static ClothesResponseDTO.CreateClothesResultDTO toCreateClothesResultDTO(Clothes clothes) {
        return ClothesResponseDTO.CreateClothesResultDTO.builder()
                .id(clothes.getId())
                .build();
    }
}
