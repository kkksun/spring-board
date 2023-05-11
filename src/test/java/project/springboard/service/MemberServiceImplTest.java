package project.springboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.service.MemberService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceImplTest {

    @Autowired
    private MemberService memberServiceImpl;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void findMember() {
        
        MemberDTO member = new MemberDTO();
        member.setLoginId("test");
        member.setPassword("test");


        MemberDTO findMember = memberServiceImpl.findMember(member);

        assertThat(findMember.getLoginId()).isEqualTo(member.getLoginId());
        assertTrue(passwordEncoder.matches(member.getPassword(), findMember.getPassword()));

    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 - 회원이 없는 경우")
    void findNoMember() {

        MemberDTO member = new MemberDTO();
        member.setLoginId("test2");
        member.setPassword("test2");

        MemberDTO findMember = memberServiceImpl.findMember(member);

        assertThat(findMember).isNull();
    }



    @Test
    @DisplayName("회원 가입")
    public void saveMember() {
        MemberDTO mebmer = MemberDTO.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .userName("테스트계정")
                .email("test@gmail.com")
                .type(MemberType.USER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberServiceImpl.saveMember(mebmer);
    }
    
    @Test
    @DisplayName("회원 정보 수정")
    public void editMember() {
        MemberDTO member = MemberDTO.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .userName("테스트계정")
                .email("test@gmail.com")
                .type(MemberType.USER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberServiceImpl.saveMember(member);

        MemberDTO findMember = memberServiceImpl.findMember(member);

        member.setPassword(passwordEncoder.encode("test1234"));
        member.setUserName("테스트");
        member.setEmail("test1@gmail.com");

        memberServiceImpl.editMember(findMember.getId(), member, MemberType.USER);

        findMember = memberServiceImpl.findMember(findMember.getId());

        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertTrue(passwordEncoder.matches("test1234", findMember.getPassword()));

    }

    @Test
    @DisplayName("회원 삭제")
    public void deleteMember() {
        MemberDTO member = MemberDTO.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .userName("테스트계정")
                .email("test@gmail.com")
                .type(MemberType.USER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberServiceImpl.saveMember(member);

        MemberDTO findMember = memberServiceImpl.findMember(member);


        memberServiceImpl.deleteMember(findMember.getId());

        MemberDTO deleteMember = memberServiceImpl.findMember(findMember);

        assertThat(deleteMember).isNull();
    }

//    @Test
//    @DisplayName("아이디 중복 확인")
//    public void loginIdDuplicateCheck(){
//        String loginId = "admin";
//        boolean loginIdDuplicateCheck = memberServiceImpl.loginIdDuplicateCheck(loginId);
//
//    }

}