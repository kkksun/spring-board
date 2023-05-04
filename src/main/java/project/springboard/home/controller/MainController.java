package project.springboard.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.JoinForm;
import project.springboard.member.domain.form.LoginForm;
import project.springboard.member.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }

    /**
     * admin 계정 생성
     */
    @PostConstruct
    public void init() {
        memberService.saveAdmin();
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String mainLogin( @ModelAttribute("loginMember") LoginForm form) {

        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(defaultValue = "/board?page=1") String redirectURL,
                        @Validated @ModelAttribute("loginMember") LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);

            return "member/login";
        }
        MemberDTO loginMember = MemberDTO.builder()
                                       .loginId(form.getLoginId())
                                       .password(form.getPassword())
                                       .build();

        MemberDTO member = memberService.findMember(loginMember);

        if (member != null) {
            if (member.getMsg() == null) {
                HttpSession session = request.getSession();
                //세션에는 필요한 정보만 담기 위해 DTO를 따로 생성
                session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginSessionDTO(member));

                return "redirect:" + redirectURL;
            } else {
                String keyName = member.getMsg().keySet().toArray()[0].toString();
                bindingResult.reject(keyName, member.getMsg().get(keyName));

                return "member/login";
            }
        } else {
                 bindingResult.reject("mismatch", "아이디 또는 비밀번호가 일치하지않습니다. 다시 입력해주세요.");

            return "member/login";
        }
    }

    /**
     * 회원 가입
     */
    @GetMapping("/join")
    public String joinForm( @ModelAttribute("joinMember") JoinForm join) {

        return "member/join";
    }

    @PostMapping("/join")
    public String joinMember(@Validated @ModelAttribute("joinMember") JoinForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);

            return "member/join";
        }

        MemberDTO joinMember = MemberDTO.builder()
                                       .loginId(form.getLoginId())
                                       .password(form.getPassword())
                                       .userName(form.getUserName())
                                       .email(form.getEmail())
                                       .type(form.getType())
                                       .status(MemberStatus.STANDBY)
                                       .build();
        memberService.saveMember(joinMember);

        return "notice/joinComplete";
    }

    /**
     * 로그인아이디 중복 체크
     */
    @PostMapping("/member/loginIdDuplicateCheck")
    @ResponseBody
    public boolean loginIdDuplicateCheck(@RequestParam("loginId") String loginId) {

        boolean checkResult = memberService.loginIdDuplicateCheck(loginId);

        return checkResult;
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

}
