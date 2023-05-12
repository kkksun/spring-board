package project.springboard.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.form.*;
import project.springboard.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 전체 회원 리스트
     */
    @GetMapping("/list/members")
    public List<MemberForm> allMemberList() {
        return memberService.allMemberList().stream().map(MemberForm::new).collect(Collectors.toList());
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/find/member/{memberId}")
    public MemberDTO memberInfo(@PathVariable Long memberId) {
        MemberDTO findMember = memberService.findMember(memberId);

        return findMember;
    }

    /**
     * 마이페이지 - 회원 정보 수정
     */

    @PatchMapping("/edit/member/{memberId}")
    public ResponseEntity<Map<String, String>> editMember(@PathVariable Long memberId, @RequestBody @Validated EditMemberForm member, BindingResult bindingResult){
        Map<String, String> msg = new HashMap<>();

        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        MemberDTO editMember = new MemberDTO(member);
        memberService.editMember(memberId, editMember, member.getRequestedPage());

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete/member/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId,@RequestParam("isMember") boolean isMember, HttpServletRequest request)  {
        memberService.deleteMember(memberId);
        if(isMember) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }

        return "ok";
    }

}



