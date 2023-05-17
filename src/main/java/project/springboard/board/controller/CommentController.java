package project.springboard.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.board.service.CommentService;

import java.util.ArrayList;
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
    public CommentForm addComment(@RequestBody CommentForm comment) {
        CommentDTO addComment = new CommentDTO(comment);
        CommentDTO commentDTO = commentService.addComment(addComment);
        CommentForm savedComment = new CommentForm(commentDTO);

        return savedComment;
    }

    /**
     * 댓글 수정
     */
    @PatchMapping("/comment/edit/{commentId}")
    public CommentForm editComment( @PathVariable("commentId") Long commentId, @RequestBody CommentForm comment) {
        CommentDTO editComment = new CommentDTO(comment);
        CommentDTO commentDTO = commentService.editComment(commentId, editComment);
        CommentForm editedComment = new CommentForm(commentDTO);

        return editedComment;
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);

        return "ok";
    }

}
