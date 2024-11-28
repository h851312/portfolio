package jongwoo.shop.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.member.username = :username order by o.orderDate desc") // 날짜 순으로 내림차순
//    @Query("select o from Order o " +
//            "where o.member.email = :email " +
//            "order by o.orderDate desc"
//    )
    List<Order> findOrders(@Param("username") String username, Pageable pageable);

    @Query("select count(o) from Order o where o.member.username = :username")
    Long countOrder(@Param("username") String username);

}
