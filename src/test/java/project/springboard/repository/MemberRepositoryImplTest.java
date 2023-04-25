package project.springboard.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.member.entity.Member;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class MemberRepositoryImplTest {

    @Autowired
    MemberRepositoryImpl memberRepositoryImpl ;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    @Transactional
    @DisplayName("회원 저장")
    void saveMember() {
        Member member = Member.builder()
                .loginId("test7")
                .password(passwordEncoder.encode("test"))
                .email("test@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();

        memberRepositoryImpl.saveMember(member);

        Optional<Member> findMember = memberRepositoryImpl.findByMember(member.getLoginId());

        assertTrue(findMember.equals(Optional.of(member)));
        assertThat(member.getLoginId()).isEqualTo(findMember.get().getLoginId());

    }

    @Test
    @Transactional
    @DisplayName("loinId로 회원 정보 조회")
    void findByMember() {
        Optional<Member> member = memberRepositoryImpl.findByMember("admin");

        assertThat(member.get().getId()).isEqualTo(1);
        assertThat(member.get().getLoginId()).isEqualTo("admin");
        assertTrue(passwordEncoder.matches("admin", member.get().getPassword()));
    }

    @Test
    @Transactional
    @DisplayName("loginId로 회원 정보 조회시 없는 경우 null 반환")
    void findMember() {
        Optional<Member> member = memberRepositoryImpl.findByMember("test1");

        assertThat(member).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("전체 회원 조회")
    void allMember() {
        Member member1 = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();
        Member member2 = Member.builder()
                .loginId("test2")
                .password(passwordEncoder.encode("test"))
                .email("test2@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();
        memberRepositoryImpl.saveMember(member1);
        memberRepositoryImpl.saveMember(member2);

        List<Member> memberList = memberRepositoryImpl.allMember();
        assertThat(memberList.size()).isSameAs(4);
    }

   /* @Test
    @Transactional
    @DisplayName("관리자 - 회원 정보 수정")
    void manageMemberEdit() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();
        memberRepositoryImpl.saveMember(member);
        Long memberId = member.getId();

        Member edditMember = Member.builder()
                .email("test22@gmail.com")
                .userName("테스트")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.MANAGER)
                .build();
        memberRepositoryImpl.eidtManageMember(memberId, edditMember);

        assertThat(member.getEmail()).isEqualTo(edditMember.getEmail());
        assertThat(member.getUserName()).isEqualTo(edditMember.getUserName());
        assertThat(member.getStatus()).isEqualTo(edditMember.getStatus());
        assertThat(member.getType()).isEqualTo(edditMember.getType());
    }*/

   /* @Test
    @Transactional
    @DisplayName("마이페이지 회원 정보 수정")
    void memberEdit() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();
        memberRepositoryImpl.saveMember(member);
        Long memberId = member.getId();

        Member edditMember = Member.builder()
                .password(passwordEncoder.encode("test"))
                .email("test22@gmail.com")
                .userName("테스트")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.MANAGER)
                .build();
        memberRepositoryImpl.editMember(memberId, edditMember);

        assertTrue(passwordEncoder.matches("test", member.getPassword()));
        assertThat(member.getEmail()).isEqualTo(edditMember.getEmail());
        assertThat(member.getUserName()).isEqualTo(edditMember.getUserName());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.STANDBY);
        assertThat(member.getType()).isEqualTo(MemberType.USER);
    }*/

    @Test
    @Transactional
    @DisplayName("회원 삭제")
    void deleteMember() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();
        memberRepositoryImpl.saveMember(member);
        Long memberId = member.getId();

        memberRepositoryImpl.deleteMember(memberId);

        Member findMember = memberRepositoryImpl.findByMember(memberId);

        assertThat(findMember).isNull();

    }
}