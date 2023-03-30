package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springboard.domain.member.MemberStatus;
import project.springboard.dto.MemberDTO;
import project.springboard.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.adminSave();
    }

    @GetMapping("/login")
    public String loginMember(Model model) {
        return "member/login";
    }

    /**
     * 로그인
     * */
    @PostMapping("/login")
    public String loginMember(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        Optional<MemberDTO> member = memberService.findMember(memberDTO);

        if(member.isPresent() && member.get().getStatus() == MemberStatus.ACTIVE) {
            session.setAttribute("member", member);
            return "redirect:/board";
        } else if(member.isPresent() && member.get().getStatus() == MemberStatus.STANDBY){

            model.addAttribute("msg", "승인이 완료되지 않았습니다. 관리자에게 문의해주세요.");
            System.out.println(model.getAttribute("msg"));
            return "member/login";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호가 일치하지않습니다. 다시 입력해주세요.");
            return "member/login";
        }
    }
    
    @GetMapping("/join")
    public String saveMember(Model model) {
        return "member/join";
    }

    /**
     * 회원 등록
     * */
    @PostMapping("/join")
    public String saveMember(@ModelAttribute MemberDTO memberDTO) {

        memberService.saveMember(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/member/loginIdCheck")
    public @ResponseBody boolean loginIdCheck(@RequestParam("loginId") String loginId) {
        boolean checkResult = memberService.loginIdCheck(loginId);
        return checkResult;
    }


    @GetMapping("/manageMember")
    public String allMember(Model model) {
        List<MemberDTO> memberList = memberService.allMember();
        System.out.println("memberList" + memberList.get(0));
        model.addAttribute("memberList", memberList);
        return "member/manageMember";
    }

    @GetMapping("/logout")
    public String logoutMember(HttpServletRequest request) {
        System.out.println("=============================");
        System.out.println(request.getSession().getId());

//        System.out.println(session.getAttribute(member));
        return"redirect:/";
    }
}
