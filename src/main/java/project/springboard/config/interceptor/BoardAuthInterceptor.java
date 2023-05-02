package project.springboard.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import project.springboard.domain.member.SessionConst;
import project.springboard.domain.member.dto.LoginSessionDTO;
import project.springboard.domain.member.entity.MemberType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Slf4j
public class BoardAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        LoginSessionDTO loginMember = (LoginSessionDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Map<String, Long> attribute = (Map<String, Long>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        Long memberId = Long.parseLong(attribute.get("memberId"));
        log.info("attribute = {}", attribute);

        if(loginMember.getId() != attribute.get("memberId") && loginMember.getType() == MemberType.USER ) {
            response.sendRedirect("/board/view/"+attribute.get("boardId"));
            return false;
        }

        return true;
    }
}
