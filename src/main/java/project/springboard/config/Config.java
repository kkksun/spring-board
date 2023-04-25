package project.springboard.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.springboard.config.interceptor.BoardAuthInterceptor;
import project.springboard.config.interceptor.LoginSessionInterceptor;


@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginSessionInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/css/**", "/error", "/login", "/join", "/logout", "/member/loginIdDuplicateCheck");

        registry.addInterceptor(new BoardAuthInterceptor())
                .order(2)
                .addPathPatterns("/board/edit/**", "/board/delete/**");

    }

}
