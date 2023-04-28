package project.springboard.service;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import project.springboard.domain.board.dto.BoardDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BoardService {
    public List<BoardDTO> allBoard() ;

    public void addBoard(BoardDTO addBoard) throws IOException;

    public BoardDTO findBoard(Long boardId);

    UrlResource fileDownload(Long fileId, HttpHeaders headers) throws MalformedURLException;

    public void editBoard(Long boardId, BoardDTO editBoard, List<Long> preFileIdList) throws IOException;

    public void deleteBoard(Long boardId);
}
