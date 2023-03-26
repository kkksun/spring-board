package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springboard.domain.member.MemberStatus;
import project.springboard.domain.member.MemberType;
import project.springboard.dto.MemberDTO;
import project.springboard.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/login")
    public String login(Model model) {
        return "member/login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "member/join";
    }

    @PostMapping("/join")
    public String addMember(@ModelAttribute MemberDTO memberDTO) {
        if(memberDTO.getSelectType().equals("M")) {
            memberDTO.setType(MemberType.MANAGER);
        } else {
            memberDTO.setType(MemberType.USER);
        }
        memberDTO.setStatus(MemberStatus.STANDBY);
        System.out.println(memberDTO.toString());
        memberService.saveMember(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/member/loginIdCheck")
    public @ResponseBody String loginIdCheck(@RequestParam("loginId") String loginId) {
        String checkResult = memberService.loginIdCheck(loginId);
        return checkResult;
    }
}
