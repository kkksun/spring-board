package project.springboard.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final EntityManager em;

    @Override
    public List<Board> boardList() {
        return em.createQuery("select b from Board b join fetch b.member m where b.delCheck = :delCheck", Board.class)
                .setParameter("delCheck", Check.N)
                .getResultList();
    }

    @Override
    public void addBoard(Board addBoard) {
        em.persist(addBoard);
    }


    public List<AttachFile> findFileList(Long boardId) {
        return em.createQuery("select f from AttachFile f where f.delCheck = :delCheck and f.board.id = :boardId", AttachFile.class)
                .setParameter("delCheck", Check.N)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Board findBoard(Long boardId) {
       return em.createQuery("select b from Board  b join fetch b.member where b.delCheck=:delCheck and b.id = :boardId", Board.class)
                       .setParameter("delCheck", Check.N)
               .setParameter("boardId", boardId)
               .getSingleResult();
    }

    @Override
    public AttachFile fileDownload(Long fileId) {
        return em.find(AttachFile.class, fileId);
    }


    @Override
    public Long allBoardCount() {
         return em.createQuery("select count(b) from Board b where b.delCheck = :delCheck", Long.class)
                .setParameter("delCheck", Check.N)
                .getSingleResult();
    }

    @Override
    public List<Board> findBoardPaging(int offset, int limit) {
        return em.createQuery("select b from Board b join fetch b.member m where b.delCheck = :delCheck order by b.createDt desc", Board.class)
                 .setParameter("delCheck", Check.N)
                 .setFirstResult(offset-1)
                 .setMaxResults(limit)
                 .getResultList();
    }
}