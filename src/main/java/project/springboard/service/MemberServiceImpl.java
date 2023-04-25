package project.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    /**admin 계정 생성 */
    @Override
    public void adminSave() {

        Optional<Member> admin = memberRepository.findByMember("admin");

        if(admin.isEmpty()) {
            Member adminMember = Member.builder()
                                       .loginId("admin")
                                       .password(passwordEncoder.encode("admin"))
                                       .userName("admin")
                                       .email("admin@gmail.com")
                                       .type(MemberType.ADMIN)
                                       .status(MemberStatus.ACTIVE)
                                       .build();

            memberRepository.saveMember(adminMember);

            Member testMember = Member.builder()
                    .loginId("test")
                    .password(passwordEncoder.encode("test"))
                    .userName("test")
                    .email("test@gmail.com")
                    .type(MemberType.USER)
                    .status(MemberStatus.STANDBY)
                    .build();

            memberRepository.saveMember(testMember);


        }
    }


    /**
     * 로그인 - loginId와 password로 회원 조회
     */
    @Override
    public MemberDTO findMember(MemberDTO member) {
        Optional<Member> findMember = memberRepository.findByMember(member.getLoginId());

        if(findMember.isPresent() && passwordEncoder.matches(member.getPassword(),findMember.get().getPassword())) {
            MemberDTO loginMember = new MemberDTO().toMemberDTO(findMember);
            if(loginMember.getStatus() != MemberStatus.ACTIVE) {
                loginMember.addMsg();
            }
            return loginMember;
        } else {
            return null;
        }
    }


    /**
     * 회원 등록
     * */
    // 결과에 대한 return이 있는게 더 나은지
    public void saveMember(MemberDTO saveMember) {
        Member memberEntity = Member.toMemberEntity(saveMember);
        String passwordEncode  = passwordEncoder.encode(memberEntity.getPassword());
        memberEntity.setPassword(passwordEncode);
        memberRepository.saveMember(memberEntity);

    }


/**
 * 중복아이디 체크 */
    public boolean loginIdDuplicateCheck(String loginId) {
        Optional<Member> member = memberRepository.findByMember(loginId);
        return member.isEmpty()? true : false;
    }




    /**
     * 전체 회원 조회
     */
    @Override
    public List<MemberDTO> allMember() {
        List<Member> allMember = memberRepository.allMember();
        return allMember.stream()
                        .map(MemberDTO::toMemberDTO)
                        .collect(toList());

    }

    /**
     * 아이디로 회원 조회*/
    @Override
    public MemberDTO findMember(Long memberId) {
        return MemberDTO.toMemberDTO(memberRepository.findByMember(memberId));
    }

    /**
     * 회원 관리 - 회원 정보 수정
     */
    // 관리자와 회원이 수정하는 범위가 다름, 구분자 파라미터를 추가하여 하나로 통합할 지 따로 할 지 생각해보기
    @Override
    public void editManageMember(Long memberId, MemberDTO editMEmber) {
        Member member = memberRepository.findByMember(memberId);

        if(editMEmber.getPassword() != null) {
            member.setPassword(editMEmber.getPassword());
        }
        member.setEmail(editMEmber.getEmail());
        member.setUserName(editMEmber.getUserName());
        member.setType(editMEmber.getType());
        member.setStatus(editMEmber.getStatus());
    }

    /**
     * 마이 페이지 - 회원 수정
     */
    @Override
    public void editMember(Long memberId, MemberDTO editMEmber) {
        Member member = memberRepository.findByMember(memberId);

        if(editMEmber.getPassword() != null) {
            member.setPassword(editMEmber.getPassword());
        }
        member.setEmail(editMEmber.getEmail());
        member.setUserName(editMEmber.getUserName());
        member.setModifyDt(editMEmber.getModifyDt());
    }

    /**
     * 마이 페이지 - 회원 삭제
     */
    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteMember(memberId);
    }
}
