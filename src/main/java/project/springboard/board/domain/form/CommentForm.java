package project.springboard.board.domain.form;


import lombok.Data;

@Data
public class CommentForm {
    private Long memberId;
    private Long boardId;
    private String comment;
    private Long parentId;

}