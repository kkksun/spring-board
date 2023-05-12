package project.springboard.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardPageController {
    /**
     * 메인 페이지 - 게시판
     */
    @GetMapping("/board")
    public String boardHome(@RequestParam("page") String page, Model model) {
        int currentPage = (page == null || page.equals("")) ? 1 : Integer.parseInt(page);
        model.addAttribute("page", currentPage);

        return "board/mainBoard";
    }
}
