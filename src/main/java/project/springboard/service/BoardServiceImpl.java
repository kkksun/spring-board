package project.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.board.form.BoardListForm;
import project.springboard.repository.BoardRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> allBoard() {
//        List<Board> boards = boardRepository.boardList();

        return boardRepository.boardList().stream()
                                          .filter(b -> b.getDelCheck() == Check.N)
                                          .map(board -> new BoardDTO(board))
                                          .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                                          .collect(Collectors.toList());
    }
}
