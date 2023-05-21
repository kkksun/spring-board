package project.springboard.board.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.entity.AttachFile;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.repository.MemberRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    public BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("게시글 등록 - 첨부파일이 없는 경우")
    public void addBoard() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.USER)
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                           .member(member)
                           .title("test 글")
                           .content("test 글")
                           .delCheck(Check.N)
                           .build();

        boardRepository.save(board);

        assertThat(board.getId()).isNotNull();
        assertThat(board.getAttachFileList().size()).isEqualTo(0);
    }
    @Test
    @Transactional
    @DisplayName("게시글 등록 - 첨부파일이 있는 경우")
    public void addBoardIncludeFile() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.USER)
                .build();
        memberRepository.save(member);

        List<AttachFile> fileList = new ArrayList<>();
        AttachFile file1 = AttachFile.builder()
                                     .originalFileName("testFile1.txt")
                                     .serverFileName(UUID.randomUUID()+ ".txt")
                                     .delCheck(Check.N)
                                     .build();
        AttachFile file2 = AttachFile.builder()
                                     .originalFileName("testFile2.txt")
                                     .serverFileName(UUID.randomUUID()+ ".txt")
                                     .delCheck(Check.N)
                                     .build();
        fileList.add(file1);
        fileList.add(file2);

        BoardDTO addBoard = new BoardDTO();
        addBoard.setTitle("test 글");
        addBoard.setContent("test 글");
        addBoard.setDelCheck(Check.N);

        Board board = Board.createBoard(addBoard, member, fileList);

        boardRepository.save(board);

        assertThat(board.getId()).isNotNull();



    }

    @Test
    @Transactional
    @DisplayName("게시글 전체 리스트 조회")
    public void boardList() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.USER)
                .build();
        memberRepository.save(member);

        Board board1 = Board.builder()
                .member(member)
                .title("test 글")
                .content("test 글")
                .delCheck(Check.N)
                .build();
        Board board2 = Board.builder()
                .member(member)
                .title("test 글")
                .content("test 글")
                .delCheck(Check.N)
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);


        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("게시글 조회")
    public void findViewBoard() {
        Member member = Member.builder()
                .loginId("test1")
                .password(passwordEncoder.encode("test"))
                .email("test1@gmail.com")
                .userName("테스트계정")
                .status(MemberStatus.ACTIVE)
                .type(MemberType.USER)
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .member(member)
                .title("test 글")
                .content("test 글")
                .delCheck(Check.N)
                .build();
        boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getId()).get();

        assertThat(findBoard).isEqualTo(board);
    }
    
    

}