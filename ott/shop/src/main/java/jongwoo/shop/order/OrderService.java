package jongwoo.shop.order;

import jongwoo.shop.item.*;
import jongwoo.shop.member.Member;
import jongwoo.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;    // 상품 정보가 담겨있는 저장소
    private final MemberRepository memberRepository;    // 주문자 정보가 담겨있는 저장소
    private final OrderRepository orderRepository;  // 주문 정보가 담겨있는 저장소
    private final ItemImgRepository itemImgRepository;
    private final ItemVideoRepository itemVideoRepository;


    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
//        ↑ 주문하려는 아이템을 itemRepository 에서 itemId 로 조회, 존재하지 않으면 EntityNotFoundException

//         주문자
//        Member member = memberRepository.findByEmail(email);   // 로그인으로 인해 주문자가 무조건 있기 때문에 orElseThrow 불필요
        Member member = memberRepository.findByUsername(username);
//        주문을 생성하는 회원 memberRepository 에서 이메일을 이용해 조회

        List<OrderItem> orderItemList = new ArrayList<>();
//        주문 항목 리스트를 생성

        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
//        주문 항목을 생성 OrderItem 클래스의 createOrderItem 메서드를 호출해서 주문항목을 생성
//        이때 필요한 정보 -> 아이템과 주문 수량

        orderItemList.add(orderItem);
//        생성한 주문 항목을 주문 항목 리스트에 추가

        Order order = Order.createOrder(member, orderItemList); // 주문을 생성
//        Order 클래스의 createOrder 메서드를 호출하여 주문을 생성하며,
//        이때 회원 정보와 주문 항목 리스트가 제공

        orderRepository.save(order);
//        생성한 주문을 데이터베이스에 저장

        return order.getId();
//        생성된 주문의 ID를 반환
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String username, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(username, pageable);
//        주문 레포지토리에서 입력받은 메일 주소와 페이징 정보를 기반으로 주문 목록 조회
        Long totalCount = orderRepository.countOrder(username);
//        유저의 주문 총 개수를 구함

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
//            조회된 주문 목록을 순회하면서 각 주문에 대한 정보를 orderHistDto 로 변환
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
//            해당 주문에 속한 모든 주문항목을 조회
            for (OrderItem orderItem : orderItems) {
//                조회된 주문 항목을 순회하면서
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                ItemVideo itemVideo = itemVideoRepository.findByItemId(orderItem.getItem().getId());
//                findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y"); 대표 이미지 조회
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl(), itemVideo.getVideoUrl());
//                orderItem 과 이미지 주소 조회
                orderHistDto.addOrderItemDto(orderItemDto);
//                생성된 orderItemDto 를 orderHistDto 리스트에 추가
            }
            orderHistDtos.add(orderHistDto);
//            orderHistDto 리스트에 추가
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
//        생성된 orderHistDto 리스트를 페이징 처리하여 반환
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String username) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String username = userDetails.getUsername();
        Member curmember = memberRepository.findByUsername(username);
//        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new); // 아이디가 있는지 없는지 예외처리 필수 아니면 Optional 로 받아야 한다.
        Member savedMember = order.getMember();
        if (!StringUtils.equals(curmember.getUsername(),savedMember.getUsername())){
            return false;
        }
        return true;
    }


    public Long orders(List<OrderDto> orderDtoList, String username) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username);

//        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }
        Order order = Order.createOrder(member, orderItemList);

        orderRepository.save(order);

        return order.getId();

    }
}
