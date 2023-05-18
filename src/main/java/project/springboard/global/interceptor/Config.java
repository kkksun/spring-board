package project.springboard.global.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginSessionInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/js/**","/css/**", "/error", "/login", "/join", "/logout", "/api/**", "/complete/**");
/*        registry.addInterceptor(new BoardAuthInterceptor())
                .order(2)
                .addPathPatterns("/board/edit/{memberId}/{boardId}**", "/board/delete/{memberId}/{boardId}**");*/

        registry.addInterceptor(new ManageAuthInterceptor())
                .order(2)
                .addPathPatterns("/manage/**");

    }

}
