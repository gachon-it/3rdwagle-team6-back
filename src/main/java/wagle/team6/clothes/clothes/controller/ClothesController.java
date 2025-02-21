package wagle.team6.clothes.clothes.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wagle.team6.clothes.clothes.apiPayload.ApiResponse;
import wagle.team6.clothes.clothes.domain.Clothes;
import wagle.team6.clothes.clothes.dto.ClothesRequestDTO;
import wagle.team6.clothes.clothes.dto.ClothesResponseDTO;
import wagle.team6.clothes.clothes.service.ClothesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    @Operation(summary = "이미지 업로드 API", description = "이미지를 업로드하는 API 입니다.")
    @PostMapping("/image")
    public ApiResponse<ClothesResponseDTO.CreateClothesResultDTO> updateMonthBudget(Long id, @RequestBody ClothesRequestDTO.CreateClothesDTO request) {
        Clothes clothes = clothesService.createClothes(1L, request);
        return ApiResponse.onSuccess(ClothesResponseDTO.toCreateClothesResultDTO(clothes));
    }
}
