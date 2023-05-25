package project.springboard.board.domain.form;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.entity.Check;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private Check delCheck;
    @JsonFormat(shape =JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape =JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;
    private List<CommentForm> childCommentList;

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
        this.modifiedDate = commentDTO.getModifiedDate();
        this.delCheck = commentDTO.getDelCheck();
        if(commentDTO.getParent() != null) {
            this.parentId = commentDTO.getParent().getId();
        }
        if(commentDTO.getChildCommentList() != null) {
            this.childCommentList =  commentDTO.getChildCommentList().stream().map(CommentForm::new).collect(Collectors.toList());
        }
    }

    public static List<CommentForm> toCommentFormList(List<CommentDTO> commentList) {
        return commentList.stream().map(CommentForm::new).collect(Collectors.toList());
    }
}