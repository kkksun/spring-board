package project.springboard.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.global.exception.CustomException;
import project.springboard.global.exception.ErrorCode;
import project.springboard.member.domain.form.RequestedPage;
import project.springboard.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**admin 계정 생성 */
    @Override
    @Transactional
    public void saveAdmin() {

        Optional<Member> admin = memberRepository.findByLoginId("admin");

        if(admin.isEmpty()) {
            Member adminMember = Member.builder()
                                       .loginId("admin")
                                       .password(passwordEncoder.encode("admin"))
                                       .userName("admin")
                                       .email("admin@gmail.com")
                                       .type(MemberType.ADMIN)
                                       .status(MemberStatus.ACTIVE)
                                       .build();

            memberRepository.save(adminMember);

            Member testMember = Member.builder()
                    .loginId("test")
                    .password(passwordEncoder.encode("test"))
                    .userName("test")
                    .email("test@gmail.com")
                    .type(MemberType.USER)
                    .status(MemberStatus.STANDBY)
                    .build();

            memberRepository.save(testMember);


        }
    }


    /**
     * 로그인
     */
    @Override
    @Transactional
    public MemberDTO findMember(MemberDTO member) {
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        if(findMember.isPresent() && passwordEncoder.matches(member.getPassword(),findMember.get().getPassword())) {
            MemberDTO loginMember =  MemberDTO.toMemberDTO(findMember.get());
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
    @Transactional
    public void saveMember(MemberDTO member) {
        Member saveMember = Member.toMemberEntity(member);
        String passwordEncode  = passwordEncoder.encode(saveMember.getPassword());
        saveMember.setPassword(passwordEncode);
        memberRepository.save(saveMember);

    }


    /**
     * 중복아이디 체크 */
    public boolean loginIdDuplicateCheck(String loginId) {

        return memberRepository.existsByLoginId(loginId);
    }


    /**
     * 전체 회원 조회
     */
    @Override
    public List<MemberDTO> allMemberList() {
        List<Member> allMemberList = memberRepository.findAll();
        return allMemberList.stream()
                        .map(MemberDTO::toMemberDTO)
                        .collect(toList());

    }


    /**
     * 아이디로 회원 조회*/
    @Override
    public MemberDTO findMember(Long memberId) {
        return MemberDTO.toMemberDTO(memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)));
    }


    /**
     * 회원 수정
     */
    @Override
    @Transactional
    public void editMember(Long memberId, MemberDTO editMember, RequestedPage requestedPage) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if(editMember.getPassword() != null) {editMember.setPassword(passwordEncoder.encode(editMember.getPassword())); }
        member.updateMember(editMember, requestedPage);
    }

    /**
     * 회원 삭제
     */
    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));;
        String loginId = member.getLoginId();
        Integer loginIdLength = memberRepository.findDeletedLoginLid(loginId.substring(0,1), loginId.substring(loginId.length() - 1));
        member.deleteMember(loginIdLength);
    }
}
