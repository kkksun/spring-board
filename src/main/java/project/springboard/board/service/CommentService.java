package project.springboard.board.service;


import project.springboard.board.domain.dto.CommentDTO;

import java.util.List;


public interface CommentService {

    List<CommentDTO> commentList(Long boardId);

    CommentDTO addComment(CommentDTO addComment);

    CommentDTO editComment(Long commentId, CommentDTO editComment);

    void deleteComment(Long commentId);

}
