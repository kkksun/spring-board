package project.springboard.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.LoginForm;
import project.springboard.member.domain.form.MemberForm;

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
        return "member/login";
    }

    /**
     * 회원 가입
     */
    @GetMapping("/join")
    public String joinForm( @ModelAttribute("joinMember") MemberForm join) {
        return "member/join";
    }
}
