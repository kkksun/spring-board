package project.springboard.member.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.entity.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private  MemberRepository memberRepository;

    @Test
    public void findMemberByLoginId() {

        memberRepository.findByLoginId("admin");
    }

    @Test
    public void loginIdDuplicateCheck() {
        boolean result = memberRepository.existsByLoginId("admin");

        Assertions.assertThat(result).isTrue();

    }


}