package project.springboard.board.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Comment;

import java.util.*;

@SpringBootTest
//@RequiredArgsConstructor
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    
    @Test
    public void findComment() {
        Comment findComment = commentRepository.findById(1L).get();
        System.out.println(findComment.getParent());
    }

    @Test
    @Transactional
    public void commentList() {
        Long boardId = 5L;
        List<Comment> comments = commentRepository.commentListByBoardId(boardId);

        List<CommentDTO> commentList = new ArrayList<>();
        Map<Long, CommentDTO> stepComment = new HashMap<>();

        comments.stream().forEach(c -> {
            CommentDTO comment = CommentDTO.toCommentDto(c);
            stepComment.put(comment.getId(), comment);
            if(c.getParent() != null) {
                if(!stepComment.containsKey(c.getParent().getId())) {
                }
                stepComment.get(c.getParent().getId()).getChildCommentList().add(comment);
            }else {
                commentList.add(comment);
            }
        });
    }



}