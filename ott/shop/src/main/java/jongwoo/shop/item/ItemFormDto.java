package jongwoo.shop.item;

import jongwoo.shop.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.List;

import static jongwoo.shop.item.QItem.item;

@Getter
@Setter
public class ItemFormDto {
    private Long id;
    private String itemCategory;
    private String itemNm;
    private Integer price;
    private String itemDetail;

    private ItemSellStatus itemSellStatus;
    private Member member;
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    private List<Long> itemIds = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();
    private List<ItemVideoDto> itemVideoDtoList = new ArrayList<>();
//    private ItemVideoDto itemVideoDto= new ItemVideoDto();
    private List<Long> itemVideoIds = new ArrayList<>();
//    private Long itemVideoId;
    private static ModelMapper modelMapper = new ModelMapper();

    public  Item createItem(){
        return modelMapper.map(this,Item.class );
    }
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
