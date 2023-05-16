package project.springboard.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.board.service.CommentService;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping("/comment/add")
    public CommentForm addComment(@RequestBody CommentForm comment) {

        CommentDTO addComment = new CommentDTO(comment);
        CommentDTO commentDTO = commentService.addComment(addComment);

        CommentForm savedComment = new CommentForm(commentDTO);
        return savedComment;

    }
}
