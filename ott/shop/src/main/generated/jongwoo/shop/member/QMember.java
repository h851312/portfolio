package jongwoo.shop.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1398828661L;

    public static final QMember member = new QMember("member1");

    public final jongwoo.shop.board.QBaseTimeEntity _super = new jongwoo.shop.board.QBaseTimeEntity(this);

    public final ListPath<jongwoo.shop.board.BoardComment, jongwoo.shop.board.QBoardComment> boardCommentList = this.<jongwoo.shop.board.BoardComment, jongwoo.shop.board.QBoardComment>createList("boardCommentList", jongwoo.shop.board.BoardComment.class, jongwoo.shop.board.QBoardComment.class, PathInits.DIRECT2);

    public final ListPath<jongwoo.shop.board.Board, jongwoo.shop.board.QBoard> boardList = this.<jongwoo.shop.board.Board, jongwoo.shop.board.QBoard>createList("boardList", jongwoo.shop.board.Board.class, jongwoo.shop.board.QBoard.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<jongwoo.shop.item.Item, jongwoo.shop.item.QItem> itemList = this.<jongwoo.shop.item.Item, jongwoo.shop.item.QItem>createList("itemList", jongwoo.shop.item.Item.class, jongwoo.shop.item.QItem.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath role = createString("role");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

