package jongwoo.shop.cart;

import jongwoo.shop.board.BaseEntity;
import jongwoo.shop.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
//    회원 한명당 1개의 장바구니를 갖으므로 처음 장바구니에 상품을 담을때 해당 회원의 장바구니를 만듦
//    Cart 엔티티에 회원 엔티티를 파라미터로 받아서 장바구니 엔티티의 생성 로직 추가


}