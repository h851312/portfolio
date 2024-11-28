package jongwoo.shop.item;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemVideoDto {
    private Long id;
    private String videoName;
    private String oriVideoName;
    private String videoUrl;

    public static ModelMapper modelMapper = new ModelMapper();

    public static ItemVideoDto of(ItemVideo itemVideo){
        return modelMapper.map(itemVideo,ItemVideoDto.class);

    }

}