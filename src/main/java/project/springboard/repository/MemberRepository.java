package project.springboard.repository;

import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void saveMember(Member memberEntity);
    Member findByMember(Long id);
    Optional<Member> findByMember(String loginId);

    List<Member> allMember();

    Member deleteMember(Long memberId);



}
