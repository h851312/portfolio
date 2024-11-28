package jongwoo.shop.board;

import jongwoo.shop.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String boardCategory;
    private String title;
    private String content;
    private String createdBy;
    private Long countVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 외래키를 설정
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardComment> boardCommentList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

    @Builder
    public Board(String boardCategory,String title, String content, String createdBy, Long countVisit, Member member, List<BoardComment> boardCommentList) {
        this.boardCategory = boardCategory;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.countVisit = countVisit;
        if (this.member != null) {
            member.getBoardList().remove(this);
        }
        this.boardCommentList = boardCommentList;

    }




    public void updateVisit(Long countVisit) {
        this.countVisit = countVisit;
    }

}