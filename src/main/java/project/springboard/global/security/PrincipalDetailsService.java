package project.springboard.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService  implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).get();

        if(member != null) {
            member.setRole("ROLE_" + member.getType());
            return new PrincipalDetails(member);
        }
        return null;
    }
}
