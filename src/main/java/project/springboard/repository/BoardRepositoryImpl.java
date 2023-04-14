package project.springboard.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.domain.board.entity.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Board> boardList() {
        List<Board> boardList = em.createQuery("select b from Board b", Board.class)
                                .getResultList();
        return boardList;
    }
}
