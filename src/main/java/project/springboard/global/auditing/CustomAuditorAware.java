package project.springboard.global.auditing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Slf4j
//@RequiredArgsConstructor
public class CustomAuditorAware implements AuditorAware<Member> {

//    private final HttpServletRequest httpServletRequest;


    public Optional<Member> getCurrentAuditor() {
//        Member member = new Member();

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

//        request.getRequestURI();
        HttpSession session = request.getSession();
                LoginSessionDTO sessionMember = (LoginSessionDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = sessionMember == null ? null : Member.builder()
                        .id(sessionMember.getId())
                        .loginId(sessionMember.getLoginId())
                        .type(sessionMember.getType())
                        .build();
        return Optional.ofNullable(member);
    }

}