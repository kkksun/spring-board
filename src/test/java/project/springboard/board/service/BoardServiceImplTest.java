package project.springboard.board.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.AttachFileDTO;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.service.MemberService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;

    @BeforeEach
    public void beforeEach() throws IOException {
        MemberDTO member = memberService.findMember(1L);
        BoardDTO board = BoardDTO.builder()
                .member(member)
                .title("test 글")
                .content("test 글")
                .attachFileList(new ArrayList<>())
                .delCheck(Check.N)
                .build();
        boardService.addBoard(board);
    }


    @Test
    @Transactional
    @DisplayName("게시글 저장")
    public void addBoard() throws IOException {
        List<AttachFileDTO> attachfileList = new ArrayList<>();
        MemberDTO member = memberService.findMember(1L);
        BoardDTO board = BoardDTO.builder()
                .title("테스트")
                .content("테스트")
                .member(member)
                .attachFileList(attachfileList).build();
        boardService.addBoard(board);
        List<BoardDTO> boardList  = boardService.allBoard();

        assertThat(boardList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제 - 댓글 있는 경우 ")
    public void deleteBoard() throws IOException {
        List<BoardDTO> boardList  = boardService.allBoard();

        BoardDTO board = boardList.get(0);
        MemberDTO member = memberService.findMember(1L);

        CommentDTO comment = CommentDTO.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        List<CommentDTO> commentList = commentService.addComment(comment);

        boardService.deleteBoard(board.getId());

        List<BoardDTO> boardList2  = boardService.allBoard();

        assertThat( boardList2.get(0).getDelCheck()).isEqualTo(Check.Y);
    }
    @Test
    @Transactional
    @DisplayName("게시글 삭제 - 댓글이 없는 경우")
    public void deleteBoard2() throws IOException {
        List<BoardDTO> boardList  = boardService.allBoard();

        BoardDTO board = boardList.get(0);
        MemberDTO member = memberService.findMember(1L);

        boardService.deleteBoard(board.getId());

        List<BoardDTO> boardList2  = boardService.allBoard();

        assertThat( boardList2.size()).isEqualTo(0);
    }





}