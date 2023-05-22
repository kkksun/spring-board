package project.springboard.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.board.service.CommentService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 조회
     */
    @GetMapping("/comment/list/{boardId}")
    public List<CommentForm> commentList(@PathVariable Long boardId) {
        return CommentForm.toCommentFormList(commentService.commentList(boardId));
    }

    /**
     * 댓글 등록
     */
    @PostMapping("/comment/add")
    public List<CommentForm> addComment(@RequestBody CommentForm comment) {
        CommentDTO addComment = new CommentDTO(comment);

        return CommentForm.toCommentFormList(commentService.addComment(addComment));
    }

    /**
     * 댓글 수정
     */
    @PatchMapping("/comment/edit/{commentId}")
    public List<CommentForm> editComment( @PathVariable("commentId") Long commentId, @RequestBody CommentForm comment) {
        CommentDTO editComment = new CommentDTO(comment);

        return CommentForm.toCommentFormList(commentService.editComment(commentId, editComment));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comment/delete/{commentId}")
    public List<CommentForm> deleteComment(@PathVariable("commentId") Long commentId) {
        return CommentForm.toCommentFormList(commentService.deleteComment(commentId));
    }

}
