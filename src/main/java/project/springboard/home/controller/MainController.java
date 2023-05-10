package project.springboard.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.JoinForm;
import project.springboard.member.domain.form.LoginForm;
import project.springboard.member.domain.form.MemberForm;
import project.springboard.member.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
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
    public ModelAndView mainLogin( @ModelAttribute("loginMember") LoginForm form) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("member/login");

        return mv;
    }

    /**
     * 회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(//@RequestParam(defaultValue = "/board?page=1") String redirectURL,
                                                     @RequestBody @Validated MemberForm form, BindingResult bindingResult,
                                                     HttpServletRequest request) {
        Map<String, String> msg = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            log.info("errors = {}", bindingResult);

            return new ResponseEntity<>(msg, HttpStatus.OK);
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
            } else {
                msg.put("global", member.getMsg());
            }
        } else {
            msg.put("global", "아이디 또는 비밀번호가 일치하지않습니다. 다시 입력해주세요.");
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 회원 가입
     */
    @GetMapping("/join")
    public ModelAndView joinForm( @ModelAttribute("joinMember") JoinForm join) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("member/join");
        mv.addObject("memberType", memberTypes());

        return mv;
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String,String>> joinMember(@RequestBody @Validated JoinForm form, BindingResult bindingResult) {

        Map<String, String> msg = new HashMap<>();

        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            log.info("errors = {}", bindingResult);

            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        MemberDTO joinMember = MemberDTO.builder()
                .loginId(form.getLoginId())
                .password(form.getPassword())
                .userName(form.getUserName())
                .email(form.getEmail())
                .type(form.getType())
                .status(MemberStatus.STANDBY)
                .build();
        System.out.println("joinMember = " + joinMember);
        memberService.saveMember(joinMember);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 로그인아이디 중복 체크
     */
    @PostMapping("/member/loginIdDuplicateCheck")
    public boolean loginIdDuplicateCheck(@RequestBody MemberForm member) {

        return memberService.loginIdDuplicateCheck(member.getLoginId());
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
