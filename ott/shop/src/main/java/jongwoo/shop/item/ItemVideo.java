package jongwoo.shop.item;

import jongwoo.shop.board.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_video")
@Getter
@Setter
public class ItemVideo extends BaseTimeEntity {

    @Id
    @Column(name = "item_video_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String videoName;

    private String oriVideoName;
    private String videoUrl;
//    private String repImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemVideo(String oriVideoName, String videoName, String videoUrl){
        this.oriVideoName = oriVideoName;
        this.videoName = videoName;
        this.videoUrl = videoUrl;
    }

}