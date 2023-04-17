package project.springboard.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.springboard.SessionConst;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.form.AddBoardForm;
import project.springboard.domain.member.dto.LoginSessionDTO;
import project.springboard.service.BoardService;

import java.util.List;

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
    public String addBoardForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) LoginSessionDTO loginMember,
                               @ModelAttribute("board") AddBoardForm form) {
        if(loginMember == null) {
            return "redirect:/login";
        }

        return "board/addBoard";
    }

    @PostMapping("/board/add")
    public String addBoard(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) LoginSessionDTO loginMember,
                           @ModelAttribute("board") AddBoardForm form) {
        if(loginMember == null) {
            return "redirect:/login";
        }

        form.setUserId(loginMember.getId());
        BoardDTO addBoard = new BoardDTO(form);

        /*for (MultipartFile multipartFile : form.getAttachFileList()) {

            System.out.println("==================START=====================");
            System.out.println(multipartFile.isEmpty());
            System.out.println("multipartFile.getName() = " + multipartFile.getName());
            System.out.println("multipartFile.getContentType() = " + multipartFile.getContentType());
            System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
            System.out.println("multipartFile.toString() = " + multipartFile.toString());
            System.out.println("==================END=====================");
            System.out.println();
        }*/
        boardService.addBoard(addBoard);


        return "notice/addComplete";
    }

}
