package project.springboard.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.board.domain.entity.Board;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.member.domain.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

//    @Builder.Default
    private MemberDTO member = new MemberDTO();

//    @Builder.Default
    private BoardDTO board = new BoardDTO();

    private String comment;

    private CommentDTO parent;

    private Long groupId;

    private Long level;

    private Long levelOrder;

    private Check delCheck;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder.Default
    private List<CommentDTO> childCommentList = new ArrayList<>();

    public CommentDTO (CommentForm comment)  {
        System.out.println("여기1");
        this.member.setId(comment.getMemberId());
        System.out.println("여기2");
        this.board.setId(comment.getBoardId());
        System.out.println("여기3");
        this.comment = comment.getComment();
        System.out.println("여기4");
        if(comment.getParentId() != null) {
            System.out.println("여기5");
            this.parent = new CommentDTO();
            parent.setId(comment.getParentId());
        }
        System.out.println("여기6");
    }

    public static CommentDTO toCommentDto(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .member(MemberDTO.toMemberDTO(comment.getMember()))
                .board(BoardDTO.toBoardDTO(comment.getBoard()))
                .comment(comment.getComment())
                .groupId(comment.getGroupId())
                .level(comment.getLevel())
                .levelOrder(comment.getLevelOrder())
                .delCheck(comment.getDelCheck())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();

        if(comment.getParent() != null) {
            commentDTO.setParent(CommentDTO.toCommentDto(comment.getParent()));
        }

        return  commentDTO;

    }
}
