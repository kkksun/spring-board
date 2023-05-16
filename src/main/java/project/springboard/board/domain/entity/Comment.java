package project.springboard.board.domain.entity;


import lombok.*;
import project.springboard.board.domain.dto.CommentDTO;
import project.springboard.board.domain.dto.CommentLevelDTO;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="COMMENTS")
public class Comment extends Auditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
     private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @Column(name = "GROUP_ID")
    private Long groupId;

    private Long level;

    @Column(name = "LEVEL_ORDER")
    private Long levelOrder;

    @Column(name = "DEL_YN")
    private Check delCheck;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> childCommentList = new ArrayList<>();


    public static Comment createComment(Member member, Board board, CommentDTO addComment, CommentLevelDTO commentLevel, Comment parent) {

        Comment comment = new Comment();
        comment.setComment(addComment.getComment());
        comment.setMember(member);
        comment.setBoard(board);
        comment.setDelCheck(Check.N);
        if(parent != null) {
            comment.setParent(parent);
        }

        if(addComment.getParent() == null) {
            comment.setGroupId( commentLevel.getParentGroupId() == null ? 1 : commentLevel.getParentGroupId() + 1);
            comment.setLevel(Long.valueOf(0) );
            comment.setLevelOrder(commentLevel.getParentLevelOrder() == null ? 1 : commentLevel.getParentLevelOrder()+1);
        } else {
            comment.setGroupId(commentLevel.getParentGroupId());
            comment.setLevel(commentLevel.getChildLevel() != null ? commentLevel.getChildLevel() : (commentLevel.getParentLevel() + 1));
            comment.setLevelOrder(commentLevel.getChildLevelOrder() != null ? (commentLevel.getChildLevelOrder()+1) : Long.valueOf(1));
        }
        return comment ;
    }
}
