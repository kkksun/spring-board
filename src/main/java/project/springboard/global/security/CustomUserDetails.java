package project.springboard.global.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import project.springboard.member.domain.entity.MemberType;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String loginId;
    private String password;
    private MemberType type;
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

          return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    public Long getId() {
        return this.id;
    }

    public MemberType getType() {
        return type;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return false;
    }

}
