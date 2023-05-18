package project.springboard.global.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextListener;
import project.springboard.member.domain.entity.Member;

import javax.servlet.http.HttpServletRequest;

//@Configuration
//@EnableJpaAuditing
//@RequiredArgsConstructor
public class AuditingConfig {

//    private final HttpServletRequest httpServletRequest;

    @Bean
    public AuditorAware<Member> auditorAware() {
        return new CustomAuditorAware();
    }


}
