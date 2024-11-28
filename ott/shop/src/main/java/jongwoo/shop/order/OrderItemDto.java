package jongwoo.shop.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private String itemCategory;
    private String itemNm; // 상품명
    private String itemDetail;
    private int count; // 주문 수량

    private int orderPrice; // 주문 금액
    private String imgUrl; // 상품 이미지 경로
    private String videoUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl, String videoUrl){
        this.itemCategory = orderItem.getItem().getItemCategory();
        this.itemNm = orderItem.getItem().getItemNm();
        this.itemDetail = orderItem.getItem().getItemDetail();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
        this.videoUrl= videoUrl;
    }
    // 주문 상품 정보를 담을 dto
    // 주문 항목에 대한 데이터 전달 및 표현을 위해 사용
}
