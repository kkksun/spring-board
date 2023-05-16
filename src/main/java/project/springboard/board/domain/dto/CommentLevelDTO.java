package project.springboard.board.domain.dto;


import lombok.Data;

@Data
public class CommentLevelDTO {

    private Long parentGroupId;
    private Long parentLevel;
    private Long parentLevelOrder;
    private Long ChildGroupId;
    private Long childLevel;
    private Long childLevelOrder;
}
