package project.springboard.board.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Comment;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.repository.MemberRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    public void beforeInit() {
        Member member = memberRepository.findByLoginId("admin").get();
        Board board = Board.builder()
                .title("test")
                .content("test")
                .member(member)
                .build();
        boardRepository.save(board);
    }

    @Test
    @DisplayName("댓글 저장 ")
    @Transactional
    public void addComment() {
        List<Board> boardList = boardRepository.findAll();
        Member member = memberRepository.findByLoginId("admin").get();
        Board board = boardList.get(0);

        Comment comment = Comment.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        commentRepository.save(comment);

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(comment).isEqualTo(findComment);
        assertThat(findComment.getParent()).isNull();
    }



    @Test
    @Transactional
    public void commentList() {

        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        Member member = memberRepository.findByLoginId("admin").get();

        Comment comment = Comment.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        commentRepository.save(comment);
        Comment comment2 = Comment.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        commentRepository.save(comment2);
        Comment comment3 = Comment.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .parent(comment)
                .build();
        commentRepository.save(comment3);



        Long boardId = board.getId();
        List<Comment> comments = commentRepository.commentListByBoardId(boardId);

        List<Comment> commentList = new ArrayList<>();
        Map<Long, Comment> stepComment = new HashMap<>();

        comments.stream().forEach(c -> {
            stepComment.put(comment.getId(), comment);
            if(c.getParent() != null) {
                if(!stepComment.containsKey(c.getParent().getId())) {
                }
                stepComment.get(c.getParent().getId()).getChildCommentList().add(comment);
            }else {
                commentList.add(comment);
            }
        });

        assertThat(commentList.size()).isEqualTo(2);
    }


    @Test
    @Transactional
    @DisplayName("댓글 수정")
    public void editComment() {
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        Member member = memberRepository.findByLoginId("admin").get();

        Comment comment = Comment.builder()
                .comment("comment test")
                .board(board)
                .member(member)
                .build();
        commentRepository.save(comment);
        comment.setComment("댓글 수정");

        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getComment()).isEqualTo("댓글 수정");
    }

}