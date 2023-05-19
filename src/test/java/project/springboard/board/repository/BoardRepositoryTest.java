package project.springboard.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.springboard.board.domain.entity.Board;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    public BoardRepository boardRepository;

    @Test
    public void findBoard(){
        Long boardId = 7L;
        Board fondBoard = boardRepository.findBoardById(boardId).get();

    }

}