package project.springboard.board.repository;

import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.board.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    CommentLevelDTO referGroupIdAndLevelId(Long BoardId, Long parentId);

    List<Comment> commentListByBoardId(Long boardId);

    void childCommentsUpdate(Long parentId);
}
