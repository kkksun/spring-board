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

    public void findBoard(Long addBoard) {

    }

    public List<AttachFile> findFileList(Long boardId) {
        return em.createQuery("select f from AttachFile f where f.board.id = :boardId", AttachFile.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    // 첨부파일이 없는 게시글도 있으니깐 첨부파일 테이블 join은 제외
    public Board viewBoard(Long boardId) {
        return em.createQuery("select b from Board  b join fetch b.member where b.id = :boardId", Board.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }
}
