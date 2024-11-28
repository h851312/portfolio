package jongwoo.shop.item;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
// ItemSearchDto itemSearchDto 검색조건 DTO
// jpa 에서 제공하는 페이징과 정렬 정보를 갖고 있는 Pageable 객체(번호, 페이지 크기, 정렬 조건) 사용
// 페이징된 결과를 Page 객체로 반환, 이 Page 객체는 페이징 정보와 함께 실제 검색결과와
// Item 엔티티를 담고 있음
