package project.springboard.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
//        .cors().disable()
        .csrf().disable()
        .formLogin()
            .loginPage("/login") // 사용자 정의 로그인 페이지
            .usernameParameter("loginId") // 아이디 파라미터명 설정
            .passwordParameter("password") // 비밀번호 파라미터명 설정
            .loginProcessingUrl("/login") //로그인 form Action url
            .defaultSuccessUrl("/board?page=") // 로그인 성공 후 이동 페이지
            .successForwardUrl("/board?page=") //로그인 성공 url
            .failureHandler(new ExtensibleAuthenticationFailureHandler());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
