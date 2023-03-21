package project.springboard.domain.board;


import lombok.Getter;
import lombok.Setter;
import project.springboard.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Member member;

    @Embedded
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn")
    private Check delCheck;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_yn")
    private Check noticeCheck;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "modify_dt")
    private Date modifyDt;

    @OneToMany(mappedBy = "board")
    private List<AttachFile> attachFileList = new ArrayList<>();
}
