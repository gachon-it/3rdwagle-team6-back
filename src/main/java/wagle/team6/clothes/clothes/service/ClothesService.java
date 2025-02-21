package wagle.team6.clothes.clothes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagle.team6.clothes.clothes.domain.Category;
import wagle.team6.clothes.clothes.domain.Clothes;
import wagle.team6.clothes.clothes.dto.ClothesRequestDTO;
import wagle.team6.clothes.clothes.repository.CategoryRepository;
import wagle.team6.clothes.clothes.repository.ClothesRepository;
import wagle.team6.clothes.member.domain.Member;
import wagle.team6.clothes.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Clothes createClothes(Long memberId, ClothesRequestDTO.CreateClothesDTO request) {
        //member가 존재하는 지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Clothes clothes = ClothesRequestDTO.toClothes(request);

        //1차, 2차 카테고리를 받아서 해당하는 카테고리 고유 id를 저장해야 함.
        Category category = categoryRepository.findByCategoryKeyAndCategoryKey2(request.getCategoryKey(), request.getCategoryKey2())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        clothes.setMember(member);
        clothes.setCategory(category);

        return clothesRepository.save(clothes);
    }

}
