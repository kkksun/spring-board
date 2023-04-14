package project.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.member.entity.Member;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.MemberType;
import project.springboard.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;


    /**admin 계정 생성 */
    @Override
    public void adminSave() {

        Optional<Member> admin = memberRepository.findByMember("admin");

        if(admin.isEmpty()) {
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

            MemberDTO testMember = MemberDTO.builder()
                    .loginId("test")
                    .password("test")
                    .userName("test")
                    .email("test@gmail.com")
                    .type(MemberType.USER)
                    .status(MemberStatus.STANDBY)
                    .build();
            Member testMemberEntity = Member.toMemberEntity(testMember);

            memberRepository.saveMember(testMemberEntity);


        }
    }


    /**
     * 로그인 loginId와 password로 회원 조회
     */
    @Override
    public MemberDTO findMember(MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findByMember(memberDTO.getLoginId());
        if(member.isPresent() && memberDTO.getPassword().equals(member.get().getPassword())) {
            return new MemberDTO().toMemberDTO(member);
        } else {
            return null;
        }
    }


    /**
     * 회원 등록
     * */

    public void saveMember(MemberDTO memberDTO) {
        Member memberEntity = Member.toMemberEntity(memberDTO);
        memberRepository.saveMember(memberEntity);

    }


/**
 * 중복아이디 체크 */
    public boolean loginIdDuplicateCheck(String loginId) {
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
    public MemberDTO findMember(Long memberId) {
        MemberDTO member = MemberDTO.toMemberDTO(memberRepository.findByMember(memberId));
        return member;
    }

    /**
     * 회원 관리 - 회원 정보 수정
     */
    // 관리자와 회원이 수정하는 범위가 다름, 구분자 파라미터를 추가하여 하나로 통합할 지 따로 할 지 생각해보기
    @Override
    public void manageMemberEdit(Long memberId, MemberDTO editMEmber) {
         memberRepository.manageMemberEdit(memberId, Member.toMemberEntity(editMEmber));
    }

    /**
     * 마이 페이지 - 회원 수정
     */
    @Override
    public void memberEdit(Long memberId, MemberDTO editMEmber) {
        memberRepository.memberEdit(memberId, Member.toMemberEntity(editMEmber));
    }

    /**
     * 마이 페이지 - 회원 삭제
     */
    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteMember(memberId);
    }
}
