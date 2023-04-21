package project.springboard.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.springboard.config.filter.LoginSessionFilter;
import project.springboard.config.interceptor.LoginSessionInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginSessionInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/css/**", "/error", "/login", "/join", "/logout", "/member/loginIdDuplicateCheck");
    }
}
