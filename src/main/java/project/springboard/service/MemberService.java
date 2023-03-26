package project.springboard.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.member.Member;
import project.springboard.dto.MemberDTO;
import project.springboard.repository.MemberRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor

public class MemberService {


    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(MemberDTO memberDTO) {

        Member memberEntity = Member.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    @Transactional
    public String loginIdCheck(String loginId) {
        Optional<Member> member = memberRepository.findByMember(loginId);
        if(member.isEmpty()) {
            return "ok";
        } else {
            return "false";
        }
    }
}
