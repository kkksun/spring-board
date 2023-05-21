package project.springboard.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.QAttachFile;

import javax.persistence.EntityManager;

import static project.springboard.board.domain.entity.QAttachFile.attachFile;

@RequiredArgsConstructor
public class AttachFileRepositoryImpl implements AttachFileCustomRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public void updateDelStatusOfFile(Long boardId) {

        queryFactory.update(attachFile)
                .set(attachFile.delCheck, Check.Y)
                .where(attachFile.board.id.eq(boardId))
                .execute();
        em.flush();
        em.clear();

    }
}
