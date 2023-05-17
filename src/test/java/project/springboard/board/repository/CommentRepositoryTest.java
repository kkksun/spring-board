package project.springboard.board.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.springboard.board.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@RequiredArgsConstructor
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    
    @Test
    public void findComment() {
        Comment byId = commentRepository.findById(1L).get();
        System.out.println("byId == null = " + byId == null);
    }

    @Test
    public void commentList() {
        Long boardId = 5L;
        List<Comment> comments = commentRepository.commentListByBoardId(boardId);
        for (Comment comment : comments) {
            System.out.println(comment.getId());
            System.out.println(comment.getMember());
            System.out.println(comment.getBoard());
            System.out.println(comment.getParent());
            System.out.println(comment.getComment());
        }
    }



}