package project.springboard.repository;

import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;

import java.util.List;

public interface BoardRepository {

    public List<Board> boardList ();

    public void addBoard(Board addBoard);

    public Board findBoard(Long boardId);

    AttachFile fileDownload(Long fileId);
}
