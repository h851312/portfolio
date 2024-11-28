package jongwoo.shop.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVideoRepository extends JpaRepository<ItemVideo, Long> {
    List<ItemVideo> findByItemIdOrderByIdAsc(Long itemId);
//    상품 동영상 아이디의 오름차순위로 가져오는 쿼리 메소드

//    List<ItemVideo> findByItemId(Long itemId);
    ItemVideo findByItemId(Long itemId);
////    findBy 뒤에 조건을 붙이면 이를 해석하여 데이터베이스 조회 조건을 자동 생성
////    itemId : 조회할 ItemImg 엔티티의 itemId 값
}