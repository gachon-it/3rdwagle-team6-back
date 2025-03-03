package wagle.team6.clothes.clothes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wagle.team6.clothes.clothes.domain.Category;
import wagle.team6.clothes.clothes.domain.Clothes;
import wagle.team6.clothes.clothes.domain.Image;
import wagle.team6.clothes.clothes.dto.ClothesRequestDTO;
import wagle.team6.clothes.clothes.repository.CategoryRepository;
import wagle.team6.clothes.clothes.repository.ClothesRepository;
import wagle.team6.clothes.clothes.repository.ImageRepository;
import wagle.team6.clothes.member.domain.Member;
import wagle.team6.clothes.member.repository.MemberRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Transactional
    public Clothes createClothes(Long memberId, MultipartFile imageFile, ClothesRequestDTO.CreateClothesDTO request) throws IOException {
        //member가 존재하는 지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Clothes clothes = ClothesRequestDTO.toClothes(request);

        //1차, 2차 카테고리를 받아서 해당하는 카테고리 고유 id를 저장해야 함.
        Category category = categoryRepository.findByCategoryKeyAndCategoryKey2(request.getCategoryKey(), request.getCategoryKey2())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        clothes.setMember(member);
        clothes.setCategory(category);

        //이미지 저장하는 서비스
        //resources\static\images 아래에
        String imagePath = imageService.saveImage(imageFile);

        //이미지 객체를 주는 로직
        Image image = Image.builder()
                .path(imagePath)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        clothes.setImage(image);

        imageRepository.save(image);
        return clothesRepository.save(clothes);
    }

    @Transactional
    public List<Clothes> findUpperClothes(Long memberId,Long categoryKey) {

        //1차, 2차 카테고리를 받아서 해당하는 카테고리 고유 id를 저장해야 함.
        List<Clothes> clothes = clothesRepository.findClothesByMemberAndCategory(memberId,categoryKey);


        return clothes;
    }

    @Transactional
    public List<Clothes> findLowerClothes(Long memberId,Long key1,Long key2) {

        //1차, 2차 카테고리를 받아서 해당하는 카테고리 고유 id를 저장해야 함.
        List<Clothes> clothes = clothesRepository.findClothesByMemberAndCategoryKeys(memberId,key1,key2);


        return clothes;
    }

}
