package project.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.member.Member;
import project.springboard.domain.member.MemberStatus;
import project.springboard.domain.member.MemberType;
import project.springboard.dto.MemberDTO;
import project.springboard.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;

    /**
     * 회원 등록
     * */

    public void saveMember(MemberDTO memberDTO) {
        if(memberDTO.getSelectType().equals("M")) {
            memberDTO.setType(MemberType.MANAGER);
        } else {
            memberDTO.setType(MemberType.USER);
        }
        memberDTO.setStatus(MemberStatus.STANDBY);
        Member memberEntity = Member.toMemberEntity(memberDTO);
        memberRepository.saveMember(memberEntity);

    }


/**
 * 중복아이디 체크 */
    public boolean loginIdCheck(String loginId) {
        Optional<Member> member = memberRepository.findByMember(loginId);
        if(member.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 전체 회원 조회
     */
    @Override
    public List<MemberDTO> allMember() {
        List<Member> allMember = memberRepository.allMember();
        List<MemberDTO> memberList = allMember.stream().map(MemberDTO::toMemberDTO)
                        .collect(toList());

        return memberList;
    }

    /**
     * 아이디로 회원 조회*/
    @Override
    public Optional<MemberDTO> findMember(Long memberId) {

        return Optional.empty();
    }

    /**loginId와 password로 회원 조회*/
    @Override
    public Optional<MemberDTO> findMember(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findByMember(memberDTO.getLoginId());
       if(member.isPresent() && memberDTO.getPassword().equals(member.get().getPassword())) {
           return Optional.ofNullable(MemberDTO.toMemberDTO(member));
       } else {
           return Optional.empty();
       }
    }

    /**admin 계정 생성 */
    @Override
    public void adminSave() {
        MemberDTO memberDTO = MemberDTO.builder()
                .loginId("admin")
                .password("admin")
                .userName("admin")
                .email("admin@gmail.com")
                .type(MemberType.ADMIN)
                .status(MemberStatus.ACTIVE)
                .build();
        Member memberEntity = Member.toMemberEntity(memberDTO);
        memberRepository.saveMember(memberEntity);
    }


}
