package wagle.team6.clothes.clothes.dto;

import lombok.Getter;
import wagle.team6.clothes.clothes.domain.Clothes;

public class ClothesRequestDTO {
    @Getter
    public static class CreateClothesDTO {
        private String color;

        private String location;

        private Long categoryKey;

        private Long categoryKey2;
    }

    public static Clothes toClothes(ClothesRequestDTO.CreateClothesDTO request) {
        return Clothes.builder()
                .color(request.color)
                .location(request.location)

                .build();
    }

}
