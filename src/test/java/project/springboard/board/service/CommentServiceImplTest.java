package project.springboard.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.BoardDTO;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Comment;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.service.MemberService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void beforeEach() throws IOException {
        MemberDTO member = memberService.findMember(1L);
        BoardDTO board = BoardDTO.builder()
                .title("test")
                .content("test")
                .member(member)
                .attachFileList(new ArrayList<>())
                .build();
        boardService.addBoard(board);

    }

    @Test
    @DisplayName("댓글 저장 ")
    @Transactional
    public void addComment() {
        List<BoardDTO> boardList = boardService.allBoard();
        BoardDTO board = boardList.get(0);
        MemberDTO member = memberService.findMember(1L);

        CommentDTO comment = CommentDTO.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        commentService.addComment(comment);

        List<CommentDTO> commentList = commentService.commentList(board.getId());

       Assertions.assertThat(commentList.get(0).getComment()).isEqualTo(comment.getComment());
    }

    @Test
    @DisplayName("대댓글 저장 ")
    @Transactional
    public void addComment2() {
        List<BoardDTO> boardList = boardService.allBoard();
        BoardDTO board = boardList.get(0);
        MemberDTO member = memberService.findMember(1L);

        CommentDTO comment = CommentDTO.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        List<CommentDTO> commentList = commentService.addComment(comment);

        CommentDTO childComment = CommentDTO.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .parent(commentList.get(0))
                .build();
        List<CommentDTO> commentList2 = commentService.addComment(childComment);



        assertThat(commentList.get(0)).isEqualTo(commentList2.get(0));

//        Assertions.assertThat(commentList.get(0).getComment()).isEqualTo(comment.getComment());
    }
}