package project.springboard.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.LoginForm;
import project.springboard.member.domain.form.JoinMemberForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MainPageController {

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String mainLogin(@ModelAttribute("loginMember") LoginForm form) {
        return "home/login";
    }

    /**
     * 회원 가입
     */
    @GetMapping("/join")
    public String joinForm( @ModelAttribute("joinMember") JoinMemberForm join) {
        return "home/join";
    }


    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logoutMember(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return"redirect:/login";
    }

    /**
     * 회원 가입 완료
     */
    @GetMapping("/complete/join")
    public String joinComplete() {
        return "notice/joinComplete";
    }

}
