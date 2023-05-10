package project.springboard.home.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainApiServiceImpl implements MainApiService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 중복아이디 체크 */
    public boolean loginIdDuplicateCheck(String loginId) {

        boolean result = memberRepository.existsByLoginId(loginId);
        return result;
    }

    /**
     * 로그인 - loginId와 password로 회원 조회
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

}
