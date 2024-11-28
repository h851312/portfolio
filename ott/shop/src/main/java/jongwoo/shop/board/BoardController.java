package jongwoo.shop.board;

import jongwoo.shop.member.Member;
import jongwoo.shop.member.MemberDto;
import jongwoo.shop.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;




@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentService boardCommentService;
    private final MemberRepository memberRepository;


    @GetMapping("/netflix")
    public String addNetflix() {
        return "/board/netflix";
    }

    @PostMapping("/netflix")
    public String createNetflix(@ModelAttribute BoardDto boardDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username);

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardDto.setMember(member);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=netflix&searchText=netflix";
    }

    @GetMapping("/wavve")
    public String addWavve() {
        return "/board/wavve";
    }

    @PostMapping("/wavve")
    public String createWavve(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=wavve&searchText=wavve";
    }

    @GetMapping("/tving")
    public String addTving() {
        return "/board/tving";
    }

    @PostMapping("/tving")
    public String createtving(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=tving&searchText=tving";
    }


    @GetMapping("/disney")
    public String addDisney() {
        return "/board/disney";
    }

    @PostMapping("/disney")
    public String createDisney(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=disney&searchText=disney";
    }


    @GetMapping("/tv")
    public String addTv() {
        return "/board/tv";
    }


    @PostMapping("/tv")
    public String createTv(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=tv+&searchText=tv+";
    }

    @GetMapping("/movie")
    public String addMovie() {
        return "/board/movie";
    }

    //
    @PostMapping("/movie")
    public String createMovie(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/ott?searchQuery=movie&searchText=movie";
    }


    @GetMapping("/boardList")
    public String boardList(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Board> boards = boardRepository.findByBoardCategoryContainingOrTitleContainingOrContentContainingOrderByRegTimeDesc(searchText, searchText, searchText, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);

        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/boardList";
    }

    @GetMapping("/boardContent/{id}")
    public String boardContent(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.findById(id).get();
        List<BoardComment> comments = boardCommentRepository.findCommentsBoardId(id);

        BoardDto boardDto = BoardDto.builder()
                .countVisit(board.getCountVisit() + 1)
                .build();
//
//        boardService.updateVisit(board.getId(), boardDto);

        boardService.countVisitLogic(id);

        model.addAttribute(board);
        model.addAttribute("comments", comments);

        return "/board/boardContent";
    }

    @PostMapping("/boardContent/{id}")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute BoardCommentDto boardCommentDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        String content = boardCommentDto.getContent();

        Board board = boardRepository.findById(id).get();
        Member member = memberRepository.findByUsername(username);


        LocalDateTime now = LocalDateTime.now();


        BoardCommentDto boardCommentDtoSet = BoardCommentDto.builder()
                .content(content)
                .created_by(username)
                .created_date(now)
                .delete_check('N')
                .member(member)
                .board(board)
                .build();
        boardCommentDto = boardCommentDtoSet;


        boardCommentService.saveBoardComment(boardCommentDto);

        List<BoardComment> comments = boardCommentRepository.findCommentsBoardId(id);

        model.addAttribute("comments", comments);
        model.addAttribute(board);
        return "/board/boardContent";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        if (!board.getCreatedBy().equals(username)) {
            return "redirect:/board/boardList";
        }

        model.addAttribute("boardDto", new BoardDto(board));
        return "/board/update";
    }

    @PostMapping("/update/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute BoardDto boardDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        if (!board.getCreatedBy().equals(username)) {
            return "redirect:/board/boardList";
        }

        boardService.updateBoard(id, boardDto);
        return "redirect:/board/boardContent/" + id;
    }

    //    @PostMapping("/delete/{id}")
//    public String deleteBoard(@PathVariable Long id) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String username = userDetails.getUsername();
//
//        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
//        if (!board.getCreatedBy().equals(username)) {
//            return "redirect:/board/boardList";
//        }
//
//        boardService.deleteBoardById(id);
//        return "redirect:/board/boardList";
//    }
    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        if (!board.getCreatedBy().equals(username)) {
            return "redirect:/board/boardList";
        }

        boardService.deleteBoardById(id);
        return "redirect:/board/boardList";
    }
}