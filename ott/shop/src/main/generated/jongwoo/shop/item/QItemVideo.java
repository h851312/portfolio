package jongwoo.shop.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemVideo is a Querydsl query type for ItemVideo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemVideo extends EntityPathBase<ItemVideo> {

    private static final long serialVersionUID = -1331695984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemVideo itemVideo = new QItemVideo("itemVideo");

    public final jongwoo.shop.board.QBaseTimeEntity _super = new jongwoo.shop.board.QBaseTimeEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final StringPath oriVideoName = createString("oriVideoName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final StringPath videoName = createString("videoName");

    public final StringPath videoUrl = createString("videoUrl");

    public QItemVideo(String variable) {
        this(ItemVideo.class, forVariable(variable), INITS);
    }

    public QItemVideo(Path<? extends ItemVideo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemVideo(PathMetadata metadata, PathInits inits) {
        this(ItemVideo.class, metadata, inits);
    }

    public QItemVideo(Class<? extends ItemVideo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

