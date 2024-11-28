package jongwoo.shop.board;


import jongwoo.shop.item.ItemImgDto;
import jongwoo.shop.item.ItemVideoDto;
import jongwoo.shop.member.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String boardCategory;
    private String title;
    private String content;
    private String createdBy;
    private Long countVisit;
    private Member member;
    private List<Long> boardIds = new ArrayList<>();
    private List<Long> boardImgIds = new ArrayList<>();



    @Builder
    public BoardDto(String boardCategory, String title, String content, String createdBy,Long countVisit, Member member) {
        this.boardCategory = boardCategory;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.countVisit = countVisit;
        this.member = member;

    }

    public Board toEntity(){
        return Board.builder()
                .boardCategory(boardCategory)
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .countVisit(countVisit)
                .member(member)
                .build();
    }

    public BoardDto(Board board) {
        id = board.getId();
        boardCategory= board.getBoardCategory();
        title = board.getTitle();
        content = board.getContent();
        createdBy = board.getCreatedBy();
        countVisit = board.getCountVisit();
        member=board.getMember();

    }

    public void updateVisit(Long countVisit){
        this.countVisit = countVisit;
    }
}
