package project.springboard.board.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private MemberDTO member = new MemberDTO();

    private BoardDTO board = new BoardDTO();

    private String comment;

    private CommentDTO parent;

    private Long groupNum;

    private Long level;

    private Long levelOrder;

    private Check delCheck;

    private List<CommentDTO> childCommentList = new ArrayList<>();

    public CommentDTO (CommentForm comment)  {
        this.member.setId(comment.getMemberId());
        this.board.setId(comment.getBoardId());
        this.comment = comment.getComment();
        if(comment.getParentId() != null) {
            this.parent = new CommentDTO();
            parent.setId(comment.getParentId());
        }
    }
}
