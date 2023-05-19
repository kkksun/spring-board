package project.springboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboard.board.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}
