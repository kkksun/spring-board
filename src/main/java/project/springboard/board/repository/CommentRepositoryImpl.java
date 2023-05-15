package project.springboard.board.repository;



import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.QComment;

import javax.persistence.EntityManager;

import java.util.List;

import static project.springboard.board.domain.entity.QComment.*;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentQuerydslRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public CommentDTO referGroupIdAndLevelId(Long boardId, Long parentId) {

        CommentDTO result = queryFactory
                .select(Projections.fields(CommentDTO.class,
                        comment1.groupNum.max().as("groupNum"),
                        comment1.level.max().as("level"),
                        comment1.levelOrder.max().as("levelOrder"))
                ).from(comment1)
                .where(boardIdEq(boardId), parentIdEqOrNull(parentId))
                .fetchOne();
        return result;
    }

    private BooleanExpression boardIdEq(Long boardId) {
        return boardId != null ? comment1.board.id.eq(boardId) : null;
    }

    private BooleanExpression parentIdEqOrNull(Long parentId) {
        return parentId != null ? comment1.parent.id.eq(parentId) : comment1.parent.id.isNull();
    } 
}
