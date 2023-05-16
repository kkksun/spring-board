package project.springboard.board.repository;

import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.dto.CommentLevelDTO;

import java.util.List;

public interface CommentQuerydslRepository {

    CommentLevelDTO referGroupIdAndLevelId(Long BoardId, Long parentId);
}
