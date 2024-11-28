package jongwoo.shop.member;

import jongwoo.shop.board.BaseTimeEntity;
import jongwoo.shop.board.Board;
import jongwoo.shop.board.BoardComment;
import jongwoo.shop.item.Item;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member")
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotBlank(message = "아이디를 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "아이디를 3~12자로 입력해주세요. [특수문자 X]")
    private String username;
//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "비밀번호를 3~12자로 입력해주세요.")
    private String password;
    private String email;
    private String provider;
    private String providerId;

    //    @Enumerated(EnumType.STRING)
    private String role;
//    private String roles;



    @OneToMany(mappedBy = "member") // 주인 필드 명
//    private List<Board> boardList = new ArrayList<>();
    private List<Board> boardList = new ArrayList<>();
    @OneToMany(mappedBy = "member"
//            , fetch = FetchType.LAZY
    )
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BoardComment> boardCommentList = new ArrayList<>();


    @Builder
    public Member(Long id, String username, String password, String email, List<Board> boardList, List<BoardComment> boardCommentList, String role, String provider, String providerId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
//        this.boardList = boardList;
        this.boardCommentList = boardCommentList;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member update(String email, String username){
        this.username = username;
        this.email = email;

        return this;
    }

    public Member(Long id, String username, String password, String email, String provider, String providerId, String role, List<Board> boardList, List<Item> itemList, List<BoardComment> boardCommentList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
//        this.boardList = boardList;
        this.itemList = itemList;
        this.boardList = boardList;
        this.boardCommentList = boardCommentList;
    }
}