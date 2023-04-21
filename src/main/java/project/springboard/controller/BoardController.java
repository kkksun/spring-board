package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.domain.member.SessionConst;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.form.AddBoardForm;
import project.springboard.domain.member.dto.LoginSessionDTO;
import project.springboard.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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


        BoardDTO viewBoard = boardService.viewBoard(boardId);
        log.info("board = {}", viewBoard);
        model.addAttribute("board", viewBoard);

        return "board/viewBoard";

    }
}
