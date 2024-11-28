package jongwoo.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemVideoService {
    @Value("${itemVideoLocation}")
    private String itemVideoLocation;

    private final ItemVideoRepository itemVideoRepository;
    private final FileService fileService;

    public void saveItemVideo(ItemVideo itemVideo, MultipartFile itemVideoFile) throws Exception{
        String oriVideoName = itemVideoFile.getOriginalFilename();
        String videoName = "";
        String videoUrl = "";
        if(!StringUtils.isEmpty(oriVideoName)){
            videoName = fileService.uploadFile(itemVideoLocation, oriVideoName,
                    itemVideoFile.getBytes());
            videoUrl = "/images/item/video/" + videoName;
        }

//        상품 비디오 정보 저장
        itemVideo.updateItemVideo(oriVideoName, videoName, videoUrl);
        itemVideoRepository.save(itemVideo);
    }
//    saveItemVideo(ItemVideo itemVideo - 업로드된 동영상과 관련된 상품 이미지 정보를 가진 ItemVidoe 객체입니다.
//    MultipartFile itemVideoFile - 업도르할 상품 동영상 파일을 나타내는 MultipartFile
//    oriVideoName - 업로드된 동영상 파일의 원래 파일명을 저장

//    itemVideo.updateItemVideo(oriVideoName, videoName, videoUrl);
//    업로드된 동영상 파일의 원래 파일명, 저장된 파일명, 동영상 url을 업데이트
//    업데이트된 상품 동영상 정보를 데이터베이스에 저장

    public void updateItemVideo(Long itemVideoId, MultipartFile itemVideoFile) throws Exception{
        if(!itemVideoFile.isEmpty()){
            ItemVideo savedItemVideo = itemVideoRepository.findById(itemVideoId)
                    .orElseThrow(EntityNotFoundException::new);
//            기존 동영상 정보를 가져오기

            //기존 동영상 파일 삭제
            if(!StringUtils.isEmpty(savedItemVideo.getVideoName())) {
                fileService.deleteFile(itemVideoLocation+"/"+
                        savedItemVideo.getVideoName());
            }

            String oriVideoName = itemVideoFile.getOriginalFilename();  // 새로운 동영상 파일의 원본 파일 이름을 가져온다.
            String videoName = fileService.uploadFile(itemVideoLocation, oriVideoName, itemVideoFile.getBytes());
            String videoUrl = "/images/item/video/" + videoName;
            savedItemVideo.updateItemVideo(oriVideoName, videoName, videoUrl); // -> ItemVideo 에 있는 updateItemVideo() 메서드
//           동영상 정보 엔티티의 필드를 업데이트 합니다.
        }
    }
}