package project.springboard.member.repository;

import project.springboard.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository {

    void saveMember(Member memberEntity);
    Member findByMember(Long id);
    Optional<Member> findByMember(String loginId);

    List<Member> allMemberList();

    Member deleteMember(Long memberId);



}
