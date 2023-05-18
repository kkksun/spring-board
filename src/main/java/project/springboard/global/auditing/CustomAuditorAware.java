package project.springboard.global.auditing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Slf4j
public class CustomAuditorAware implements AuditorAware<Member> {

    public Optional<Member> getCurrentAuditor() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("authentication = {}", authentication);
            log.info(" authentication.getPrincipal() = {} ",  authentication.getPrincipal());
            log.info("authentication.isAuthenticated() = {}", authentication.isAuthenticated() );
            if(authentication == null || !authentication.isAuthenticated()) {
                log.debug("Not found AuthenticationToken");
                return null;
            }

            return (Optional<Member>) authentication.getPrincipal(); // anonymousUser
    }


}
