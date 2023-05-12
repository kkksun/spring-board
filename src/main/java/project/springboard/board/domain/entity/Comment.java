package project.springboard.board.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.entity.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
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

    @Column(name = "PARENT_ID")
    private Long parentNum;

    @Column(name = "GROUP_NUM")
    private Long groupNum;

    private Long level;

    @Column(name = "LEVEL_ORDER")
    private Long levelOrder;

    @Column(name = "DEL_YN")
    private Check delCheck;


}
