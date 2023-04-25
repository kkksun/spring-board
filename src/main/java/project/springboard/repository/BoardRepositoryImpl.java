package project.springboard.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

//    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Board> boardList() {
        return em.createQuery("select b from Board b join fetch b.member", Board.class)
                                .getResultList();
    }

    @Override
    public void addBoard(Board addBoard) {
        em.persist(addBoard);
    }


    public List<AttachFile> findFileList(Long boardId) {
        return em.createQuery("select f from AttachFile f where f.board.id = :boardId", AttachFile.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Board findBoard(Long boardId) {
        return em.createQuery("select b from Board  b join fetch b.member where b.id = :boardId", Board.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    @Override
    public AttachFile fileDownload(Long fileId) {
        return em.find(AttachFile.class, fileId);
    }
}
