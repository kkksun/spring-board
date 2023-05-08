package project.springboard.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboard.member.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

}
