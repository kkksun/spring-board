package project.springboard.global.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    CustomAuditorAware auditorAware() {
        return new CustomAuditorAware();
    }
}
