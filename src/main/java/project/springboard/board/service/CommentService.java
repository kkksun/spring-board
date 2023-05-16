package project.springboard.board.service;


import org.springframework.stereotype.Service;
import project.springboard.board.domain.dto.CommentDTO;


public interface CommentService {

    CommentDTO addComment(CommentDTO addComment);

}
