package jongwoo.shop.board;

import jongwoo.shop.item.Item;
import jongwoo.shop.item.ItemFormDto;
import jongwoo.shop.item.ItemImg;
import jongwoo.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private ThreadLocal<Long> countVisitStore = new ThreadLocal<>();

    @Transactional
    public Long saveBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
        return boardDto.getId();
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Page<Board> getBoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

    public Page<Board> paging(int page) {
        return boardRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

    }

    @Transactional
    public void updateVisit(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow((() ->
                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));

        board.updateVisit(boardDto.getCountVisit());
        log.info("조회 카운트={}",boardDto.getCountVisit());
    }

    public Board findById(Long id){
        Board board = boardRepository.findById(id).get();
        return board;
    }


    @Transactional
    public Long countVisitLogic(Long id) {

        Board board = boardRepository.findById(id).orElseThrow((() ->
                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));

        log.info("저장 : ID={} board.getCountVisit={} ",id, countVisitStore.get());
        countVisitStore.set(board.getCountVisit() + 1L);
        board.updateVisit(countVisitStore.get());
        sleep(100);
        log.info("조회 : countVisitStore={}",countVisitStore.get());
        log.info("카운트 횟수={}", board.getCountVisit());

        countVisitStore.remove();
        return countVisitStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BoardDto getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(BoardDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
    }

    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }
    public void updateBoard(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
    }



}