package project.springboard.member.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.RequestedPage;


@Controller
public class MemberPageController {

    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() { return  MemberType.values(); }

    @ModelAttribute("memberStatuses")
    public MemberStatus[] memberStatus() { return  MemberStatus.values(); }

    /**
     * 회원 관리 - 전체 회원
     */
    @GetMapping("/manage/members")
    public String allMemberList(Model model) {
        return "member/manageMembers";
    }

    /**
     * 회원 관리 페이지 - 회원 정보
     */
    @GetMapping("/manage/info/{memberId}")
    public String manageMemberInfo(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", RequestedPage.MANAGE);

        return "member/memberInfo";
    }

    /**
     * 마이 페이지 - 회원 정보
     */
    @GetMapping("/member/info/{memberId}")
    public String memberInfo(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", RequestedPage.MEMBER);

        return "member/memberInfo";
    }

    /**
     * 관리 페이지 - 회원 정보 수정
     */
    @GetMapping("/manage/edit/{memberId}")
    public String edit(@PathVariable Long memberId, @RequestParam("pwChange") boolean pwChange, Model model) {
        model.addAttribute("pwChange", pwChange);
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", RequestedPage.MANAGE);

        return "member/editMember";
    }

    /**
     * 마이페이지 - 회원 정보 수정
     */
    @GetMapping("/member/edit/{memberId}")
    public String editMemberForm(@PathVariable Long memberId, @RequestParam("pwChange") boolean pwChange, Model model) {
        model.addAttribute("pwChange", pwChange);
        model.addAttribute("memberId", memberId);
        model.addAttribute("requestedPage", RequestedPage.MEMBER);

        return "member/editMember";
    }
}
