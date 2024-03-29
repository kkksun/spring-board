package project.springboard.board.repository;



import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.domain.entity.QComment;

import javax.persistence.EntityManager;
import java.util.List;

import static project.springboard.board.domain.entity.QBoard.board;
import static project.springboard.board.domain.entity.QComment.*;
import static project.springboard.member.domain.entity.QMember.*;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

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
                .where(comment1.board.id.eq(boardId).and(parentIdEqOrNull(parentId)))
                .fetchOne();
        return result;
    }

    private BooleanExpression parentIdEqOrNull(Long parentId) {
        return parentId != null ? comment1.id.eq(parentId) : comment1.parent.id.isNull();
    }

    @Override
    public List<Comment> commentListByBoardId(Long boardId) {
        QComment pComment = new QComment("pComment");
        List<Comment> commentList = queryFactory
                .select(comment1)
                .from(comment1)
                .leftJoin(comment1.parent, pComment).fetchJoin()
                .leftJoin(comment1.member, member).fetchJoin()
                .leftJoin(comment1.board, board).fetchJoin()
                .where(comment1.board.id.eq(boardId))
                .orderBy(comment1.parent.id.asc().nullsFirst(), comment1.groupId.asc(),comment1.level.asc(), comment1.levelOrder.asc())
                .fetch();
        return  commentList;
    }

    @Override
    public Long countByBoardId(Long boardId) {
        return queryFactory
                .select(comment1.count())
                .from(comment1)
                .where(comment1.board.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public void updateDelStatusOfComment(Long boardId) {
        queryFactory.update(comment1)
                .set(comment1.delCheck, Check.Y)
                .where(comment1.board.id.eq(boardId))
                .execute();

        em.flush();
        em.clear();

    }
}
