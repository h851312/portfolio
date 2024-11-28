package jongwoo.shop.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardComment is a Querydsl query type for BoardComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardComment extends EntityPathBase<BoardComment> {

    private static final long serialVersionUID = 2079028196L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardComment boardComment = new QBoardComment("boardComment");

    public final QBoard board;

    public final ListPath<BoardComment, QBoardComment> boardCommentList = this.<BoardComment, QBoardComment>createList("boardCommentList", BoardComment.class, QBoardComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final StringPath created_by = createString("created_by");

    public final DateTimePath<java.time.LocalDateTime> created_date = createDateTime("created_date", java.time.LocalDateTime.class);

    public final ComparablePath<Character> delete_check = createComparable("delete_check", Character.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final jongwoo.shop.member.QMember member;

    public QBoardComment(String variable) {
        this(BoardComment.class, forVariable(variable), INITS);
    }

    public QBoardComment(Path<? extends BoardComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardComment(PathMetadata metadata, PathInits inits) {
        this(BoardComment.class, metadata, inits);
    }

    public QBoardComment(Class<? extends BoardComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new jongwoo.shop.member.QMember(forProperty("member")) : null;
    }

}

