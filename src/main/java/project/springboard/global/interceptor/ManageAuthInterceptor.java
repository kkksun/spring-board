package project.springboard.global.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import project.springboard.member.domain.SessionConst;
import project.springboard.member.domain.dto.LoginSessionDTO;
import project.springboard.member.domain.entity.MemberType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        LoginSessionDTO loginMember = (LoginSessionDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember.getType() == MemberType.USER){
            response.sendRedirect("/board");
            return false;
        }
        return true;
    }
}
