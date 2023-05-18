package project.springboard.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.repository.MemberRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService   {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).get();
        Set< GrantedAuthority> roles = new HashSet<>();
        roles.add( (new SimpleGrantedAuthority("ROLE_" + member.getType())));
        return new CustomUserDetails(member.getId(), member.getLoginId(), member.getPassword(),member.getType(), roles);
    }
}
