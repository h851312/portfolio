package jongwoo.shop.board;

import jongwoo.shop.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "board_comment")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;
    private String content;
    private LocalDateTime created_date;
    private String created_by;
    private Character delete_check;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Member member;


    @OneToMany(mappedBy = "member")
    private List<BoardComment> boardCommentList;

    @Builder
    public BoardComment(String content, LocalDateTime created_date, String created_by, Character delete_check, Board board, Member member) {
        this.content = content;
        this.created_date = created_date;
        this.created_by = created_by;
        this.delete_check = delete_check;
        if(this.board != null){
            board.getBoardCommentList().remove(this);
        }else
            this.board = board;
        if(this.member != null){
            member.getBoardCommentList().remove(this);
        }else
            this.member = member;
    }
}