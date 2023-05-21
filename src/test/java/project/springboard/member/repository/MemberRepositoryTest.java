package project.springboard.member.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private  MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @Transactional
    @DisplayName("회원 저장")
    public void findMemberByLoginId() {
        Member member = Member.builder()
                .loginId("test3")
                .password(passwordEncoder.encode("test"))
                .email("test@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.STANDBY)
                .type(MemberType.USER)
                .build();

        memberRepository.save(member);

        Member findMember = memberRepository.findByLoginId(member.getLoginId()).get();


        assertThat(findMember).isEqualTo(member);
        assertThat(member.getLoginId()).isEqualTo(findMember.getLoginId());

    }

    @Test
    @DisplayName("중복아이디 체크")
    public void loginIdDuplicateCheck() {
        boolean result = memberRepository.existsByLoginId("test2");

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("마스킹 아이디 체크")
    @Transactional
    public void findDeletedLoginLid() {
        Member member = Member.builder()
                .loginId("test2")
                .password(passwordEncoder.encode("test"))
                .userName("test")
                .email("test@gmail.com")
                .type(MemberType.MANAGER)
                .status(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(member);


        Member findMEmber = memberRepository.findByLoginId("test2").get();
        String loginId = findMEmber.getLoginId();

        Integer deletedLoginLidCount = memberRepository.findDeletedLoginLid(loginId.substring(0,1), loginId.substring(loginId.length()-1));

        assertThat(deletedLoginLidCount).isNull();
    }


}