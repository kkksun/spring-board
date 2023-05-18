package project.springboard.global.auditing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


@Slf4j
public class SecurityAuditorAware implements AuditorAware<UserDetails> {

    public Optional<UserDetails> getCurrentAuditor() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info(" authentication.getPrincipal() = {} ",  (Optional<UserDetails>)authentication.getPrincipal());
            if(authentication == null || !authentication.isAuthenticated()) {
                log.debug("Not found AuthenticationToken");
                return null;
            }

            return (Optional<UserDetails>) authentication.getPrincipal(); // anonymousUser
    }


}
