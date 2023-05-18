package project.springboard.board.service;


import project.springboard.board.domain.dto.CommentDTO;

import java.util.List;


public interface CommentService {

    List<CommentDTO> commentList(Long boardId);

    List<CommentDTO> addComment(CommentDTO addComment);

    List<CommentDTO> editComment(Long commentId, CommentDTO editComment);

    List<CommentDTO> deleteComment(Long commentId);

}
