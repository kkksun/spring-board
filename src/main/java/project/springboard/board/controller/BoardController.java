package project.springboard.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.board.domain.form.ViewBoardForm;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.form.WriteBoardForm;
import project.springboard.global.paging.PagingParam;
import project.springboard.board.service.BoardService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 메인 페이지 - 게시판
     */
    @GetMapping("/board/list")
    public PagingParam boardHome(@RequestParam("page") String page) {
        int currentPage = (page == null || page.equals("")) ? 1 : Integer.parseInt(page);
        PagingParam pagingParam = boardService.pageBoardList(currentPage);
        pagingParam.toBoardForm();
        return pagingParam;
    }

    /**
     * 게시글 등록
     */

    @PostMapping("/board/add")
    public ResponseEntity<Map<String, String>> addBoard(@ModelAttribute @Validated WriteBoardForm board, BindingResult bindingResult) throws IOException {
        Map<String, String> msg = new HashMap<>();

        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        BoardDTO addBoard = new BoardDTO(board);
        boardService.addBoard(addBoard);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 게시글 조회
     */

    @GetMapping("/board/view/{boardId}")
    public ViewBoardForm viewBoard(@PathVariable("boardId") Long boardId) {
        ViewBoardForm findBoard = new ViewBoardForm(boardService.findBoard(boardId));
        return findBoard;
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("file/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        UrlResource resource = boardService.fileDownload(fileId, headers);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.valueOf(headers.getContentDisposition())                )
                .body(resource);
    }

    /**
     * 게시판 수정
     */
    @PatchMapping("/board/edit/{boardId}")
    public  ResponseEntity<Map<String, String>> editBoard(@PathVariable("boardId")Long boardId,
                                                          @ModelAttribute @Validated WriteBoardForm board,
                                                          BindingResult bindingResult) throws IOException {
        Map<String, String> msg = new HashMap<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        BoardDTO editBoard = new BoardDTO(board);
        boardService.editBoard(boardId, editBoard, board.getPreFileIdList());

        return  new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/board/delete/{boardId}")
    public String deleteBoard( @PathVariable("boardId")Long boardId) {
        boardService.deleteBoard(boardId);
        return "ok";
    }

}
