package jongwoo.shop.ott;


import jongwoo.shop.board.*;
import jongwoo.shop.item.*;
import jongwoo.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OttController {

    private final ItemService itemService;
    private final BoardRepository boardRepository;
    @GetMapping(value = "/itemList")
    public String netflix(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);   /* 페이지당 6개씩 출력 */
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemList";
    }


//    @GetMapping(value = "/ott")
//    public String netflix(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model,@RequestParam(required = false, defaultValue = "") String searchText) {
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);   /* 페이지당 6개씩 출력 */
//        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
//
//
//
//        model.addAttribute("items", items);
//        model.addAttribute("itemSearchDto", itemSearchDto);
//        model.addAttribute("maxPage", 5);
//
//        Page<Board> boards = boardRepository.findByBoardCategoryContainingOrTitleContainingOrContentContainingOrderByRegTimeDesc(searchText, searchText, searchText, pageable);
//
//        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
//        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);
//
//        model.addAttribute("boards", boards);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//
//
//
//        return "ott/ott";
//    }

    @GetMapping(value = "/ott")
    public String netflix(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model, @RequestParam(required = false, defaultValue = "") String searchText) {
        // 페이지 번호 설정 및 페이지 당 아이템 수 설정
        Pageable pageable = PageRequest.of(page.orElse(0), 6);   // 페이지당 6개씩 출력

        // 아이템 페이지 가져오기
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        // 게시글 페이지 가져오기
        Page<Board> boards = boardRepository.findByBoardCategoryContainingOrTitleContainingOrContentContainingOrderByRegTimeDesc(searchText, searchText, searchText, pageable);

        // 페이지네이션 범위 계산
        int currentPage = boards.getPageable().getPageNumber() + 1;
        int totalPages = boards.getTotalPages();
        int startPage = Math.max(1, currentPage - 1);
        int endPage = Math.min(totalPages, currentPage + 3);

        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "ott/ott";
    }

}
