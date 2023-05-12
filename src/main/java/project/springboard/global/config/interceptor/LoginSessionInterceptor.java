package project.springboard.global.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.springboard.member.domain.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        if(requestURI.equals("/board")) {
            requestURI = requestURI + "?page=";
        }

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
//            response.sendRedirect("/login?redirectURL=" + requestURI);
            response.sendRedirect("/login");
                return false;
            }

        return true;
    }


}
