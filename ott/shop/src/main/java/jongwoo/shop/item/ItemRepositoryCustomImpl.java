package jongwoo.shop.item;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static jongwoo.shop.item.QItem.item;
import static jongwoo.shop.item.QItemImg.itemImg;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
//    Impl - implements 약어로 구현을 나타내는 용어
//    프로그래밍에서 Impl 은 인터페이스나 추상클래스의 실제 구현체를 나타내는 클래스
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
    }
//    판매 상태를 검색하는 조건을 생성 searchSellStatus 가 주어진 경우 해당 판매상태와 일치하는 조건 생성
//    아니면 null 반환

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if (StringUtils.equals("itemNm", searchBy)){    // 상품명을 검색하는 조건
            return item.itemNm.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)){   // 작성자 검색조건
            return item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }


    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        List<Item> content = queryFactory
                .selectFrom(item) // item 엔티티 데이터를 선택
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())  // 아이템 id 의 역순 정렬
                .offset(pageable.getOffset())   // 페이지의 시작 오프셋을 설정
                .limit(pageable.getPageSize())  // 페이지의 limit 크기를 설정
                .fetch();   // 쿼리를 실행하고 그 결과를 리스트로 반환

        long total = queryFactory.select(Wildcard.count).from(item)   // 테이블에서 count 를 조회하는 쿼리
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),itemSearchDto.getSearchQuery()))
                .fetchOne();    // 카운트 결과를 단일값으로 반환
        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : item.itemNm.like("%" + searchQuery + "%");
    }
    private BooleanExpression itemCategoryLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : item.itemCategory.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        QItem item = QItem.item;
//        QItemImg itemImg = QItemImg.itemImg;
//
//        List<MainItemDto> content = queryFactory
//                .select(
//                        new QMainItemDto(
//                                item.id,
//                                item.itemCategory,
//                                item.itemNm,
//                                item.itemDetail,
//                                itemImg.imgUrl,
//                                item.price)
//                )
//                .from(itemImg)
//                .join(itemImg.item, item)
//                .where(itemImg.repImgYn.eq("Y"))    // 조건
//                .where(itemNmLike(itemSearchDto.getSearchQuery()))
//                .where(itemCategoryLike(itemSearchDto.getSearchQuery()))
//                .orderBy(item.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Long total = queryFactory
//                .select(Wildcard.count)
//                .from(itemImg)
//                .join(itemImg.item, item)
//                .where(itemImg.repImgYn.eq("Y"))
////                .where(itemNmLike(itemSearchDto.getSearchQuery()))
//                .where(itemCategoryLike(itemSearchDto.getSearchQuery()))
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, total);

        // 조건을 결합하기 위한 BooleanBuilder 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(itemImg.repImgYn.eq("Y")); // 대표 이미지 조건 추가

        // 검색 조건 추가
        if (itemSearchDto.getSearchQuery() != null) {
            booleanBuilder.and(itemNmLike(itemSearchDto.getSearchQuery())
                    .or(itemCategoryLike(itemSearchDto.getSearchQuery())));
        }

        // 콘텐츠 조회 쿼리
        List<MainItemDto> content = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemCategory,
                                item.itemNm,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(booleanBuilder)
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회 쿼리
        Long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(booleanBuilder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
//    .select(Wildcard.count) 부분은 Querydsl 에서 쿼리 결과의 개수를 가져오기
}
