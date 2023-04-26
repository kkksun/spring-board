package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.domain.board.form.EditBoardForm;
import project.springboard.domain.member.SessionConst;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.form.AddBoardForm;
import project.springboard.domain.member.dto.LoginSessionDTO;
import project.springboard.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    /**
     * 메인 페이지 - 게시판
     */
    @GetMapping("/board")
    public String boardHome(Model model) {

        List<BoardDTO> boardList = boardService.allBoard();

        model.addAttribute("boardList", boardList);

        return "board/mainBoard";
    }


    /**
     * 게시글 등록
     */
    @GetMapping("/board/add")
    public String addBoardForm(@ModelAttribute("board") AddBoardForm form) {

        return "board/addBoard";
    }

    @PostMapping("/board/add")
    public String addBoard(HttpServletRequest request,
                           @Validated @ModelAttribute("board") AddBoardForm form, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "board/addBoard";
        }

        HttpSession session = request.getSession();
        LoginSessionDTO loginMember = (LoginSessionDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        form.setUserId(loginMember.getId());
        BoardDTO addBoard = new BoardDTO(form);

        boardService.addBoard(addBoard);

        return "notice/addComplete";
    }

    /**
     * 게시글 조회
     */
    @GetMapping("board/view/{boardId}")
    public String viewBoard(@PathVariable Long boardId, Model model) {

        BoardDTO viewBoard = boardService.findBoard(boardId);
        model.addAttribute("board", viewBoard);

        return "board/viewBoard";
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
    @GetMapping("board/edit/{boardId}")
    public String editBoardForm(@PathVariable("boardId") Long boardId, Model model) {

        BoardDTO board = boardService.findBoard(boardId);

        EditBoardForm form = EditBoardForm.builder()
                .id(board.getId())
                .userId(board.getMember().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .preFileList(board.getAttachFileList())
                .build();

        model.addAttribute("board", form);
        return "board/editBoard";
    }

    @PostMapping("board/edit/{boardId}")
    public String editBoard(@PathVariable("boardId")Long boardId, @ModelAttribute("board")EditBoardForm form)  {
        return "redirect:/board/view/" + boardId;
    }


    /**
     * 게시글 삭제
     */
    @GetMapping("board/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId")Long boardId) {
        boardService.deleteBoard(boardId);
        return "notice/deleteBoardComplete";
    }

}
