package project.springboard.service;

import project.springboard.domain.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    public List<BoardDTO> allBoard() ;

    public void addBoard(BoardDTO addBoard);

//    public void editBoard();

//    public void deleteBoard();
}
