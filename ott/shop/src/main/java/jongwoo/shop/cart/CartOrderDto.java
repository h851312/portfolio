package jongwoo.shop.cart;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {
    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;
//    장바구니에 여러 개의 상품을 주문하므로 cartOrderDto 클래스가
//    자시 자신을 리스트로 가지고 있도록 만듭니다.

}
