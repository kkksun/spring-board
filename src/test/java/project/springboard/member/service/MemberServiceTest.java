package project.springboard.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.RequestedPage;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 저장")
    public void saveMember() {
        MemberDTO member = MemberDTO.builder()
                .loginId("test4")
                .password(passwordEncoder.encode("test"))
                .userName("test")
                .email("test@gmail.com")
                .type(MemberType.MANAGER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberService.saveMember(member);

        MemberDTO findMember = memberService.findMember(member);

        assertThat(member.getLoginId()).isEqualTo(findMember.getLoginId());
    }

    @Test
    @DisplayName("관리자 - 회원 정보 수정")
    public void editMemberByManager() {

        MemberDTO addMember = memberService.findMember(2L);

        MemberDTO editMember = MemberDTO.builder()
                .userName("테스트계정")
                .email("test2@gmail.com")
                .type(MemberType.ADMIN)
                .build();

        memberService.editMember(addMember.getId(), editMember, RequestedPage.MANAGE);

        MemberDTO findMember = memberService.findMember(addMember.getId());

        assertThat(findMember.getUserName()).isEqualTo(editMember.getUserName());
        assertThat(findMember.getEmail()).isEqualTo(editMember.getEmail());
        assertThat(findMember.getType()).isEqualTo(editMember.getType());
    }

    @Test
    @DisplayName("회원 정보 수정")
    public void editMemberByMember() {
        MemberDTO member = MemberDTO.toMemberDTO(Member.builder()
                .loginId("test3")
                .password(passwordEncoder.encode("test"))
                .userName("test")
                .email("test@gmail.com")
                .type(MemberType.MANAGER)
                .status(MemberStatus.ACTIVE)
                .build());
        memberService.saveMember(member);

        MemberDTO addMember = memberService.findMember(member);

        MemberDTO editMember = MemberDTO.builder()
                .userName("테스트")
                .email("test2@gmail.com")
                .password("test1234")
                .type(MemberType.ADMIN)
                .build();

        memberService.editMember(addMember.getId(), editMember, RequestedPage.MEMBER);

        MemberDTO findMember = memberService.findMember(addMember.getId());

        assertThat(findMember.getUserName()).isEqualTo(editMember.getUserName());
        assertThat(findMember.getEmail()).isEqualTo(editMember.getEmail());
        assertThat(passwordEncoder.matches("test1234", findMember.getPassword())).isTrue();
        assertThat(findMember.getType()).isNotSameAs(editMember.getType());
    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteMember() {

        MemberDTO member = MemberDTO.toMemberDTO(Member.builder()
                .loginId("test2")
                .password(passwordEncoder.encode("test"))
                .userName("test")
                .email("test@gmail.com")
                .type(MemberType.MANAGER)
                .status(MemberStatus.ACTIVE)
                .build());

        memberService.saveMember(member);

        MemberDTO findMember = memberService.findMember(member);
        memberService.deleteMember(findMember.getId());

        MemberDTO deletedMember = memberService.findMember(findMember.getId());

        assertThat(deletedMember.getLoginId()).isEqualTo("t***2");


    }
}