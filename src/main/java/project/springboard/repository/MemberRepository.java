package project.springboard.repository;

import project.springboard.domain.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepository {

    void saveMember(Member memberEntity);
    Member findByMember(Long id);
    Optional<Member> findByMember(String loginId);

    List<Member> allMember();
}
