package project.springboard.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.form.LoginForm;
import project.springboard.member.domain.form.JoinMemberForm;
import project.springboard.member.service.MemberService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    /**
     * admin 계정 생성
     */
    @PostConstruct
    public void init() {
        memberService.saveAdmin();
    }

    /**
     * 회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Validated LoginForm member, BindingResult bindingResult, HttpServletRequest request) {
        Map<String, String> msg = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            log.info("errors = {}", bindingResult);

            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        MemberDTO loginMember = new MemberDTO(member);
        MemberDTO findMember = memberService.findMember(loginMember);

        if (findMember != null) {
            if (findMember.getMsg() == null) {
                HttpSession session = request.getSession();
                //세션에는 필요한 정보만 담기 위해 DTO를 따로 생성
                session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginSessionDTO(findMember));
            } else {
                msg.put("global", findMember.getMsg());
            }
        } else {
            msg.put("global", "아이디 또는 비밀번호가 일치하지않습니다. 다시 입력해주세요.");
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<Map<String,String>> joinMember(@RequestBody @Validated JoinMemberForm member, BindingResult bindingResult) {
        Map<String, String> msg = new HashMap<>();

        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            log.info("errors = {}", bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        MemberDTO joinMember = new MemberDTO(member);
        memberService.saveMember(joinMember);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 로그인아이디 중복 체크
     */
    @PostMapping("/member/loginIdDuplicateCheck")
    public boolean loginIdDuplicateCheck(@RequestBody JoinMemberForm member) {

        return memberService.loginIdDuplicateCheck(member.getLoginId());
    }

}
