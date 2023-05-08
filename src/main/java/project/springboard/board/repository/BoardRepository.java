package project.springboard.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByDelCheck(Check delCheck, Pageable pageable);


}
