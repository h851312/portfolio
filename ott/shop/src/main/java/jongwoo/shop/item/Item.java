package jongwoo.shop.item;


import jongwoo.shop.board.BaseEntity;
import jongwoo.shop.board.BaseTimeEntity;
import jongwoo.shop.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  //상품 코드

    private String itemCategory;  //상품카테고리

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price; //가격

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품상세 설명

    private ItemSellStatus itemSellStatus; //상품 판매상태

//    private LocalDateTime regTime; // 등록시간
//
//    private LocalDateTime updateTime;  //수정시간

//    @OneToMany(mappedBy = "member")
//    private List<Item> itemList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void updateItem(ItemFormDto itemFormDto){
        this.itemCategory = itemFormDto.getItemCategory();
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
}
