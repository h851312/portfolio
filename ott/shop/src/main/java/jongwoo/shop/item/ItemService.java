package jongwoo.shop.item;

import jongwoo.shop.member.Member;
import jongwoo.shop.member.MemberDto;
import jongwoo.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import static jongwoo.shop.member.QMember.member;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    private final ItemVideoService itemVideoService;

    private final ItemVideoRepository itemVideoRepository;


    @Autowired
    private MemberRepository memberRepository;
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList, List<MultipartFile> itemVideoFileList) throws Exception {
//        상품 등록
        Item item = itemFormDto.createItem();

        itemRepository.save(item);

//        이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);  // 해당 이미지 객체에 상품 정보를 연결
            if (i == 0)
                itemImg.setRepImgYn("Y");   // 이미지 넘버가 0이면 대표이미지
            else
                itemImg.setRepImgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
//        동영상 등록
//        ItemVideo itemVideo = new ItemVideo();
//        itemVideo.setItem(item);  // 해당 이미지 객체에 상품 정보를 연결

//        itemVideoService.saveItemVideo(itemVideo, itemVideoFile);

        for (int i = 0; i < itemVideoFileList.size(); i++) {
            ItemVideo itemVideo = new ItemVideo();
            itemVideo.setItem(item);  // 해당 이미지 객체에 상품 정보를 연결

            itemVideoService.saveItemVideo(itemVideo, itemVideoFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true) // 조회만 가능하게(읽을수만 있도록)
//        상품 상세정보를 가져오는 메서드 선언
    public ItemFormDto getItemDtl(Long itemId) {
//        해당 상품에 연결된 이미지 정보를 id 순서대로 가져온다.
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId); // 여러개니까 리스트로 받음
//        ItemImgDto 객체 리스트를 초기화한다.
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
//            ItemImgDto 클래스에 정의된 of 메서드를 호출 ItemImg -> ItemImgDto 로 변환하여 반환
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
//            리스트에 추가
            itemImgDtoList.add(itemImgDto);
        }

//        해당 상품에 연결된 동영상 정보를 id 순서대로 가져온다.
//        ItemVideo itemVideo = itemVideoRepository.findByItemId(itemId);
//        ItemVideoDto  itemVideoDto= new ItemVideoDto();



        List<ItemVideo> itemVideoList = itemVideoRepository.findByItemIdOrderByIdAsc(itemId); // 여러개니까 리스트로 받음
//        ItemVideoDto 객체 리스트를 초기화한다.
        List<ItemVideoDto> itemVideoDtoList = new ArrayList<>();
        for (ItemVideo itemVideo : itemVideoList) {
//            ItemImgDto 클래스에 정의된 of 메서드를 호출 ItemVideo -> ItemVideoDto 로 변환하여 반환
            ItemVideoDto itemVideoDto = ItemVideoDto.of(itemVideo);
//            리스트에 추가
            itemVideoDtoList.add(itemVideoDto);
        }
//        해당 id 의 상품정보를 데이터베이스에서 가져옵니다. 없으면 예외처리
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
//        상품 정보를 ItemFormDto 로 변환합니다.
        ItemFormDto itemFormDto = ItemFormDto.of(item);
//        상품 정보 Dto 에 이미지 정보 DTO 리스트를 설정
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        itemFormDto.setItemVideoDtoList(itemVideoDtoList);



        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList, List<MultipartFile> itemVideoFileList) throws Exception {
//        상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();
//        이미지의 id 리스트를 가져와 itemImgIds 에 할당  ->  이미지 업데이트나 관련 작업(조회)을 한다.

//        이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
//        itemImgIds.get(i) -> 상품에 연결된 각 이미지 id
//        itemImgFileList.get(i) -> 새로운 이미지 파일
        }
        List<Long> itemVideoIds = itemFormDto.getItemVideoIds();
//        Long itemVideoIds = itemFormDto.getItemVideoIds();
//        동영상의 id 리스트를 가져와 itemVideoIds 에 할당  ->  동영상 업데이트나 관련 작업(조회)을 한다.

//        동영상 등록
        for (int i = 0; i < itemVideoFileList.size(); i++) {
            itemVideoService.updateItemVideo(itemVideoIds.get(i), itemVideoFileList.get(i));
//        itemVideoIds.get(i) -> 상품에 연결된 각 동영상 id
//        itemVideoFileList.get(i) -> 새로운 ehddudtkd 파일
        }
//        itemVideoService.updateItemVideo(itemVideoIds, itemVideoFileList);
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

}
