package project.springboard.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.*;
import project.springboard.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }


//    /**
//     * 회원 관리 - 전체 회원
//     */
//    @GetMapping("/manage/member")
//    public String allMemberList(Model model) {
//
//        List<MemberDTO> memberList = memberService.allMemberList();
//        model.addAttribute("memberList", memberList);
//
//        return "member/manageMember";
//    }


    @PostMapping("/manage/memberInfo/{memberId}")
    public String editManageMember(@PathVariable Long memberId,
                                   @Validated @ModelAttribute("memberInfo") EditManageMemberForm member,
                                   BindingResult bindingResult,
                                   Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("memberId", memberId);

            return "member/manageMemberEdit";
        }

        MemberDTO editMember = MemberDTO.builder()
                                        .loginId(member.getLoginId())
                                        .userName(member.getUserName())
                                        .email(member.getEmail())
                                        .type(member.getType())
                                        .status(member.getStatus())
                                        .build();
        if(member.getPwChange()) {
            editMember.setPassword(member.getPassword());
        }

        memberService.editMember(memberId, editMember, MemberType.MANAGER);

        return "redirect:/manage/member";

    }

    /**
     * 회원 관리 - 회원 삭제
     */
    @GetMapping("/manage/member/delete/{memberId}")
    public String deleteManageMember(@PathVariable("memberId") Long memberId, Model model)  {

        memberService.deleteMember(memberId);
        model.addAttribute("type", "MANAGE");

        return "notice/deleteMemberComplete";
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/member/{memberId}")
    public MemberDTO memberInfo(@PathVariable Long memberId) {
        MemberDTO findMember = memberService.findMember(memberId);
        return findMember;
    }

    /**
     * 마이페이지 - 회원 정보 수정
     */

    @PatchMapping("/member/{memberId}")
    public String editMember(@PathVariable Long memberId,
                             @RequestBody EditMemberForm member,
                             BindingResult bindingResult,
                             Model model){
//
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("memberId", memberId);
//            return "member/memberEdit";
//        }
//
//        MemberDTO editMember = MemberDTO.builder()
//                .loginId(member.getLoginId())
//                .userName(member.getUserName())
//                .email(member.getEmail())
//                .build();
//        if(member.getPwChange()) {
//            editMember.setPassword(member.getPassword());
//        }
//
//        memberService.editMember(memberId, editMember, MemberType.USER);

        log.info("editMember = {}", member);
        return "ok";
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/member/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId,@RequestParam("isMember") boolean isMember, HttpServletRequest request, Model model)  {

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



