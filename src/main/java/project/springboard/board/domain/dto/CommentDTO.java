package project.springboard.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.Comment;
import project.springboard.board.domain.form.CommentForm;
import project.springboard.member.domain.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private MemberDTO member;

    private BoardDTO board;

    private String comment;

    private CommentDTO parent;

    private Long groupId;

    private Long level;

    private Long levelOrder;

    private Check delCheck;

    private Long childCnt;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder.Default
    private List<CommentDTO> childCommentList = new ArrayList<>();

    public CommentDTO (CommentForm comment)  {
        this.member = new MemberDTO();
        member.setId(comment.getMemberId());
        this.board = new BoardDTO();
        board.setId(comment.getBoardId());
        this.comment = comment.getComment();
        if(comment.getParentId() != null) {
            this.parent = new CommentDTO();
            parent.setId(comment.getParentId());
        }
    }


    public static CommentDTO toCommentDto(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .member(MemberDTO.toMemberDTO(comment.getMember()))
                .board(BoardDTO.toBoardDTO(comment.getBoard()))
                .comment(comment.getDelCheck() == Check.N? comment.getComment() : "삭제된 댓글입니다.")
                .groupId(comment.getGroupId())
                .level(comment.getLevel())
                .levelOrder(comment.getLevelOrder())
                .delCheck(comment.getDelCheck())
                .childCnt(comment.getChildCnt())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();

        if(comment.getParent() != null) {
            commentDTO.setParent(CommentDTO.toCommentDto(comment.getParent()));
        }

        return  commentDTO;

    }
    public static List<CommentDTO> toCommentDtoList(List<Comment> comments) {
        List<CommentDTO> commentList = new ArrayList<>();
        Map<Long, CommentDTO> commentMap = new HashMap<>();

        comments.forEach(c -> {
            CommentDTO comment = CommentDTO.toCommentDto(c);
            commentMap.put(comment.getId(), comment);
            if(comment.getParent() != null){
                commentMap.get(comment.getParent().getId()).getChildCommentList().add(comment);
            } else {
                commentList.add(comment);
            }
        });
        return commentList;
    }
}
