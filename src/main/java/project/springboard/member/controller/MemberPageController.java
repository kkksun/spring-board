package project.springboard.member.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.EditMemberForm;
import project.springboard.member.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberPageController {

    private final MemberService memberService;

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }

    /**
     * 회원 관리 - 전체 회원
     */
    @GetMapping("/manage/member")
    public String allMemberList(Model model) {

        List<MemberDTO> memberList = memberService.allMemberList();
        model.addAttribute("memberList", memberList);

        return "member/manageMember";
    }

    /**
     * 회원 관리 페이지 - 회원 정보
     */
    @GetMapping("/manage/info/{memberId}")
    public String manageMemberInfo(@PathVariable Long memberId, Model model) {

        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", "MANAGE");

        return "member/memberInfo";

    }

    /**
     * 마이 페이지 - 회원 정보
     */
    @GetMapping("/member/info/{memberId}")
    public String memberInfo(@PathVariable Long memberId, Model model) {

        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", "MEMBER");

        return "member/memberInfo";
    }

    /**
     * 관리 페이지 - 회원 정보 수정
     */
    @GetMapping("/manage/edit/{memberId}")
    public String edit(@PathVariable Long memberId, @RequestParam("pwChange") boolean pwChange, Model model) {
        model.addAttribute("pwChange", pwChange);
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", "MANAGE");
        return "member/editMember";
    }

    /**
     * 마이페이지 - 회원 정보 수정
     */
    @GetMapping("/member/edit/{memberId}")
    public String editMemberForm(@PathVariable Long memberId, @RequestParam("pwChange") boolean pwChange, Model model) {
//        EditMemberForm memberInfo = EditMemberForm.toEditForm(memberService.findMember(memberId));
        model.addAttribute("pwChange", pwChange);
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", "MEMBER");
        return "member/editMember";
    }
}
