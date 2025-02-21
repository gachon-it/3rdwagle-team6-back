package wagle.team6.clothes.clothes.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wagle.team6.clothes.clothes.apiPayload.ApiResponse;
import wagle.team6.clothes.clothes.domain.Clothes;
import wagle.team6.clothes.clothes.dto.ClothesRequestDTO;
import wagle.team6.clothes.clothes.dto.ClothesResponseDTO;
import wagle.team6.clothes.clothes.service.ClothesService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    @Operation(summary = "이미지 업로드 API", description = "이미지를 업로드하는 API 입니다.")
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ClothesResponseDTO.CreateClothesResultDTO> updateMonthBudget(@RequestPart("memId") Long memId,
                                                                                    @RequestPart("imageFile") MultipartFile imageFile,
                                                                                    @RequestPart("request") ClothesRequestDTO.CreateClothesDTO request,
                                                                                    HttpServletRequest servletRequest) throws IOException {

        Clothes clothes = clothesService.createClothes(memId, imageFile, request);
        return ApiResponse.onSuccess(ClothesResponseDTO.toCreateClothesResultDTO(clothes));
    }
}

