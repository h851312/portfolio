package jongwoo.shop.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;



    @Transactional
    public Long saveBoardComment(BoardCommentDto dto){
        boardCommentRepository.save(dto.toEntity());
        return dto.getId();
    }

}