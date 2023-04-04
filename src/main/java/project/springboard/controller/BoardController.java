package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.springboard.SessionConst;
import project.springboard.domain.member.Member;
import project.springboard.dto.BoardDTO;
import project.springboard.dto.MemberDTO;
import project.springboard.repository.BoardRepository;
import project.springboard.service.BoardService;
import project.springboard.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/board")
    public String boardHome(HttpServletRequest request, Model model) {
//        Optional<MemberDTO> member = (Optional<MemberDTO>) session.getAttribute("member");

        HttpSession session = request.getSession(false);
        if(session == null) {
            return "redirect:/";
        }

        Optional<Member> loginMember = (Optional<Member>) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        if(loginMember == null || loginMember.isEmpty()) {
            return "redirect:/";
        }

        List<BoardDTO> boardList = boardService.allBoard();
        model.addAttribute("boardList", boardList);
        return "board/mainBoard";
    }



}
