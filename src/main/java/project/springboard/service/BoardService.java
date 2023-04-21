package project.springboard.service;

import project.springboard.domain.board.dto.BoardDTO;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    public List<BoardDTO> allBoard() ;

    public void addBoard(BoardDTO addBoard) throws IOException;

    public BoardDTO viewBoard(Long boardId);

//    public void editBoard();

//    public void deleteBoard();
}
