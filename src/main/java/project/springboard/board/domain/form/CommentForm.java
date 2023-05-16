package project.springboard.board.domain.form;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.board.domain.dto.CommentDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    private Long id;
    private Long memberId;
    private String loginId;
    private Long boardId;
    private String comment;
    private Long parentId;
    private Long groupId;
    private Long level;
    private Long levelOrder;
    @JsonFormat(shape =JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;


    public CommentForm (CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.memberId = commentDTO.getMember().getId();
        this.loginId = commentDTO.getMember().getLoginId();
        this.boardId = commentDTO.getBoard().getId();
        this.comment = commentDTO.getComment();
        this.groupId = commentDTO.getGroupId();
        this.level = commentDTO.getLevel();
        this.levelOrder = commentDTO.getLevelOrder();
        this.createdDate = commentDTO.getCreatedDate();
        if(commentDTO.getParent() != null) {
            this.parentId = commentDTO.getParent().getId();
        }
    }
}