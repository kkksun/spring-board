package project.springboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.domain.board.dto.BoardDTO;
import project.springboard.domain.board.entity.AttachFile;
import project.springboard.domain.board.entity.Board;
import project.springboard.domain.board.entity.Check;
import project.springboard.domain.member.entity.Member;
import project.springboard.repository.BoardRepository;
import project.springboard.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 게시글 리스트 조회
     */
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

    /**
     * 게시글 등록
     */
    @Override
    @Transactional
    public void addBoard(BoardDTO addBoard) throws IOException {

        Member member = memberRepository.findByMember(addBoard.getMember().getId());

        List<AttachFile> attachFiles = null;
        if(!addBoard.getAttachFileList().isEmpty()) {
            attachFiles = addBoard.getAttachFileList().stream().map(AttachFile::createAttachFile)
                            .collect(Collectors.toList());
        }

        Board board = Board.createBoard(addBoard, member, attachFiles);

       boardRepository.addBoard(board);

       if(!board.getAttachFileList().isEmpty()) {
           String path = createFilePath(board.getId());
           for (AttachFile attachFile : board.getAttachFileList()) {
               attachFile.setPath(path);
               storeFile(attachFile);
           }
       }
    }

    /**
     * 첨부 파일 저장
     */
    private void storeFile(AttachFile attachFile) throws IOException {
        String directory = fileDir + attachFile.getPath();
        String fullPath = directory + attachFile.getServerFileName();
        if(!new File(directory).exists()) {
            Files.createDirectories(Paths.get(directory));
        }
        attachFile.getMultipartFile().transferTo(new File(fullPath));
    }

    /**
     *파일 경로 생성
     */
    public String createFilePath(Long boardId) {
        return  "/" + boardId + "/file/";
    }

    /**
     * 게시글 조회
     */
    @Override
    public BoardDTO viewBoard(Long boardId) {
        Board board = boardRepository.viewBoard(boardId);
        return BoardDTO.toBoardDTO(board);
    }
}
