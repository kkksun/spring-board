package project.springboard.board.repository;



import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.board.domain.entity.QComment;

import javax.persistence.EntityManager;

import java.util.List;

import static project.springboard.board.domain.entity.QComment.*;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentQuerydslRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public CommentLevelDTO referGroupIdAndLevelId(Long boardId, Long parentId) {
        QComment childComment = new QComment("childComment");

        CommentLevelDTO result = queryFactory
                .select(Projections.fields(CommentLevelDTO.class,
                        comment1.groupId.max().as("parentGroupId"),
                        comment1.level.max().as("parentLevel"),
                        comment1.levelOrder.max().as("parentLevelOrder"),
                        childComment.groupId.max().as("childGroupId"),
                        childComment.level.max().as("childLevel"),
                        childComment.levelOrder.max().as("childLevelOrder"))
                ).from(comment1)
                .leftJoin(childComment).on(comment1.id.eq(childComment.parent.id))
                .where(boardIdEq(boardId), parentIdEqOrNull(parentId))
                .fetchOne();
        return result;
    }

    private BooleanExpression boardIdEq(Long boardId) {
        return boardId != null ? comment1.board.id.eq(boardId) : null;
    }

    private BooleanExpression parentIdEqOrNull(Long parentId) {
        return parentId != null ? comment1.id.eq(parentId) : comment1.parent.id.isNull();
    } 
}
