package project.springboard.board.service;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.global.paging.PagingParam;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BoardService {
    List<BoardDTO> allBoard() ;

    void addBoard(BoardDTO addBoard) throws IOException;

    BoardDTO findBoard(Long boardId);

    UrlResource fileDownload(Long fileId, HttpHeaders headers) throws MalformedURLException;

    void editBoard(Long boardId, BoardDTO editBoard, List<Long> preFileIdList) throws IOException;

    void deleteBoard(Long boardId);

    PagingParam boardPaging(int offset);

    List<BoardDTO> pageBoardList(int offset, int limit);
}
