package project.springboard.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.springboard.SessionConst;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.form.AddBoardForm;
import project.springboard.domain.board.form.BoardListForm;
import project.springboard.domain.member.dto.LoginSessionDTO;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.service.BoardService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/board")
    public String boardHome(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) LoginSessionDTO loginMember,
                            Model model) {

        if(loginMember == null) {
            return "redirect:/login";
        }

        List<BoardDTO> boardList = boardService.allBoard();

        model.addAttribute("boardList", boardList);

        return "board/mainBoard";
    }


    @GetMapping("/board/add")
    public String boardAddForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) LoginSessionDTO loginMember,
                               @ModelAttribute("board") AddBoardForm form) {
        if(loginMember == null) {
            return "redirect:/login";
        }

        return "board/addBoard";
    }

}
