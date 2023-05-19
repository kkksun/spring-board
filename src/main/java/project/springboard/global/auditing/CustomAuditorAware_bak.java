//package project.springboard.global.auditing;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import project.springboard.member.domain.SessionConst;
//import project.springboard.member.domain.dto.LoginSessionDTO;
//import project.springboard.member.domain.entity.Member;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//
//@Slf4j
//public class CustomAuditorAware_bak  {
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//    //    @Autowired
////    private MemberService
//    @Override
//
//    public Optional<Member> getCurrentAuditor() {
//
//        LoginSessionDTO sessionMember = (LoginSessionDTO) httpServletRequest.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
//
//        Member member = Member.builder()
//                .loginId(sessionMember.getLoginId())
//                .type(sessionMember.getType()).build();
//
//        log.info("member = {}", member);
//
//        return Optional.ofNullable(member);
//    }
//
//
//}
