package project.springboard.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 게시글 등록
     */
    @GetMapping("/board/add")
    public String addBoardForm() {
        return "board/writeBoard";
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/board/view/{boardId}")
    public String viewBoard(@PathVariable Long boardId, Model model) {
        model.addAttribute("boardId", boardId);

        return "board/viewBoard";
    }

    /**
     * 게시글 수정
     */
    @GetMapping("/board/edit/{boardId}")
    public String editBoardForm( @PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("boardId", boardId);

        return "board/writeBoard";
    }

    /**
     * 게시글 등록 /삭제 완료
     */
    @GetMapping("/complete/board")
    public String deleteComplete(@RequestParam("type") String type, Model model) {
        model.addAttribute("type", type);
        return "notice/boardComplete";
    }

}
