package project.springboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.springboard.domain.member.Member;

@Controller
public class MemberController {

    @GetMapping("/login")
    public String login(Model model) {
        return "member/login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "member/join";
    }

    @PostMapping("/join")
    public String addMember(@ModelAttribute Member member) {


        return "member/login";
    }
}
