package project.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.board.form.BoardListForm;
import project.springboard.domain.member.entity.Member;
import project.springboard.repository.BoardRepository;
import project.springboard.repository.MemberRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public List<BoardDTO> allBoard() {
//        List<Board> boards = boardRepository.boardList();

        return boardRepository.boardList().stream()
                                          .filter(b -> b.getDelCheck() == Check.N)
                                          .map(BoardDTO ::new)
                                          .sorted(Comparator.comparing(BoardDTO::getCreateDt).reversed())
                                          .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addBoard(BoardDTO addBoard) {

        Member member = memberRepository.findByMember(addBoard.getMember().getId());

        List<AttachFile> attachFiles = null;
        if(!addBoard.getAttachFileList().isEmpty()) {
            attachFiles = addBoard.getAttachFileList().stream().map(AttachFile::createAttachFile)
                            .collect(Collectors.toList());
        }

        Board board = Board.createBoard(addBoard, member, attachFiles);

       boardRepository.addBoard(board);

       if(!board.getAttachFileList().isEmpty()) {
           String path = "\\" + board.getId() + "\\";
           for (AttachFile attachFile : board.getAttachFileList()) {
               attachFile.setPath(path);
           }
       }


    }


}
