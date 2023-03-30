package project.springboard.domain.board;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn")
    private Check delCheck;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_yn")
    private Check noticeCheck;

    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private Date createDt;

    @LastModifiedDate
    @Column(name = "modify_dt")
    private Date modifyDt;

    @OneToMany(mappedBy = "board")
    private List<AttachFile> attachFileList = new ArrayList<>();
}
