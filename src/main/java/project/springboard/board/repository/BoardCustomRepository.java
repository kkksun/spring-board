package project.springboard.board.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.springboard.board.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardCustomRepository {

//    List<Board> findBoardList(Pageable pageable);
    Optional<Board> findBoardById(Long boardId);

   Page<Board> boardListOfPage(Pageable pageable);
}

