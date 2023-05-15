package project.springboard.board.repository;

import project.springboard.board.domain.dto.CommentDTO;

import java.util.List;

public interface CommentQuerydslRepository {

    CommentDTO referGroupIdAndLevelId(Long BoardId, Long parentId);
}
