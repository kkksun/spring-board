package project.springboard.board.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.QAttachFile;
import project.springboard.board.domain.entity.QBoard;

import java.util.List;
import java.util.Optional;

import static project.springboard.board.domain.entity.QAttachFile.attachFile;
import static project.springboard.board.domain.entity.QBoard.board;
import static project.springboard.member.domain.entity.QMember.member;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;
/*    public List<Board> findBoardList(Pageable pageable) {
        List<Board> boardList = queryFactory.selectFrom(board)
                .leftJoin(member).fetchJoin()
                .where(board.delCheck.eq(Check.N))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdDate.desc())
                .fetch();

        return boardList;

    }*/

    @Override
    public Optional<Board> findBoardById(Long boardId) {

        Optional<Board> findBoard = Optional.ofNullable(queryFactory.selectFrom(board)
                .leftJoin(board.member, member).fetchJoin()
                .leftJoin(board.attachFileList, attachFile).fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne());
        return findBoard;
    }
}
