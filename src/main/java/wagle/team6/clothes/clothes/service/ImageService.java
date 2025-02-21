package wagle.team6.clothes.clothes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wagle.team6.clothes.clothes.domain.Image;
import wagle.team6.clothes.clothes.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    ImageRepository imageRepository;
    // 저장 경로: 실행 시 접근 가능한 위치

    @Transactional
    public String saveImage(MultipartFile file) throws IOException {
        if(file.isEmpty()) return null;

        String filePath = "C:\\IntelliJ\\3rdwagle-team6-back\\src\\main\\resources\\static\\images\\";
        String fileUrl = "http://localhost:4000/file/";

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;

        try {

            file.transferTo(new File(savePath));

        } catch(Exception exception) {
            exception.printStackTrace();
            return null;
        }
        String url = fileUrl + saveFileName;
        return url;
        /*
        if (image.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        String fileName = image.getOriginalFilename();

        String fullPathName = "C:\\IntelliJ\\3rdwagle-team6-back\\src\\main\\resources\\static\\images" + fileName;
        image.transferTo(new File(fullPathName));

        Image imageEntity = new Image();
        imageEntity.setPath(fullPathName);
        imageEntity.setCreatedAt(LocalDateTime.now());
        imageEntity.setUpdatedAt(LocalDateTime.now());

        return imageEntity;
        */
        /*try {
            // 경로 존재 여부 확인 및 생성
            Path directory = Paths.get(IMAGE_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);  // 폴더 없으면 자동 생성
            }

            // 파일 이름 처리
            String originalFilename = imageFile.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFilename;

            // 파일 저장
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, imageFile.getBytes());

            // 반환 URL
            return "/images/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage());
        }*/
    }
}
