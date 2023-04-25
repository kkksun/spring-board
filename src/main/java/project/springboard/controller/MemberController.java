package project.springboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;
import project.springboard.domain.member.form.*;
import project.springboard.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }


    /**
     * 회원 관리 - 전체 회원
     */
    @GetMapping("/manage/member")
    public String memberManage(Model model) {

        List<MemberDTO> memberList = memberService.allMember();
//        List<MemberListForm> memberListForms = memberList.stream().map(MemberListForm :: new).collect(Collectors.toList());

        model.addAttribute("memberList", memberList);
        return "member/manageMember";
    }

    /**
     * 회원 관리 - 회원 정보 수정
     */
    @GetMapping("/manage/memberInfo/{memberId}")
    public String manageMemberInfo(@PathVariable Long memberId, Model model) {

        EditManageMemberForm memberInfo = EditManageMemberForm.toEditForm(memberService.findMember(memberId));
         model.addAttribute("memberInfo", memberInfo);
        return "member/manageMemberEdit";

    }

    @PostMapping("/manage/memberInfo/{memberId}")
    public String manageMemberEdit(@PathVariable Long memberId,
                                   @Validated @ModelAttribute("memberInfo") EditManageMemberForm member,
                                   BindingResult bindingResult,
                                   Model model) {

        log.info("memberInfo ={}", member);
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

        memberService.editManageMember(memberId, editMember);

        return "redirect:/manage/member";

    }

    /**
     * 회원 관리 - 회원 삭제
     */
    @GetMapping("/manage/member/delete/{memberId}")
    public String manageMemberDelete(@PathVariable("memberId") Long memberId, Model model)  {

        memberService.deleteMember(memberId);
        model.addAttribute("type", "MANAGE");

        return "notice/deleteComplete";
    }

    /**
     * 마이페이지 - 회원 정보 조회
     */
    @GetMapping("/member/Info/{memberId}")
    public String memberInfo(@PathVariable Long memberId, Model model) {
        MemberDTO memberInfo = memberService.findMember(memberId);
        model.addAttribute("memberInfo", memberInfo);

        return "member/memberInfo";
    }

    /**
     * 마이페이지 - 회원 정보 수정
     */
    @GetMapping("/member/edit/{memberId}")
    public String memberEditForm(@PathVariable Long memberId, @RequestParam("pwChange") boolean pwChange, Model model) {
        EditMemberForm memberInfo = EditMemberForm.toEditForm(memberService.findMember(memberId));
        memberInfo.setPwChange(pwChange);
        model.addAttribute("memberInfo", memberInfo);

        return "member/memberEdit";
    }


    @PostMapping("/member/edit/{memberId}")
    public String memberEdit(@PathVariable Long memberId,
                             @Validated @ModelAttribute("memberInfo") EditMemberForm member,
                             BindingResult bindingResult,
                             Model model){
        log.info("editMember = {}", member);

        if(bindingResult.hasErrors()) {
            model.addAttribute("memberId", memberId);
            return "member/memberEdit";
        }

        MemberDTO editMember = MemberDTO.builder()
                .loginId(member.getLoginId())
                .userName(member.getUserName())
                .email(member.getEmail())
                .build();
        if(member.getPwChange()) {
            editMember.setPassword(member.getPassword());
        }

        memberService.editMember(memberId, editMember);

        return "redirect:/member/Info/{memberId}";
    }

    /**
     * 회원 탈퇴
     */
    @GetMapping("/member/delete/{memberId}")
    public String memberDelete(@PathVariable("memberId") Long memberId,HttpServletRequest request, Model model)  {

        memberService.deleteMember(memberId);
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        model.addAttribute("type", "USER");
        return "notice/deleteComplete";
    }


}



