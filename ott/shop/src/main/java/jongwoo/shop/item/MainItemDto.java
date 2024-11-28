package jongwoo.shop.item;

import com.querydsl.core.annotations.QueryProjection;
import jongwoo.shop.board.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.StringTokenizer;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemCategory;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private String videoUrl;
    private Integer price;


    @QueryProjection
    public MainItemDto(Long id, String itemCategory,String  itemNm, String itemDetail, String imgUrl,Integer price){

        this.id = id;
        this.itemCategory = itemCategory;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}
