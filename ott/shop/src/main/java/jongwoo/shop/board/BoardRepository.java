package jongwoo.shop.board;

import jongwoo.shop.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();

    Page<Board> findByBoardCategoryContainingOrTitleContainingOrContentContainingOrderByRegTimeDesc(String boardCategory, String title, String content, Pageable pageable);

//        Board findById();
}