package project.springboard.board.repository;

import project.springboard.board.domain.entity.AttachFile;
import project.springboard.board.domain.entity.Board;

import java.util.List;

public interface BoardRepository {

    public Long allBoardCount();

    public List<Board> boardList ();

    public void addBoard(Board addBoard);

    public Board findBoard(Long boardId);

    AttachFile fileDownload(Long fileId);

    List<Board> findBoardPaging(int offset, int limit);
}
