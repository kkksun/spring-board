package project.springboard.domain.board.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.springboard.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "modify_dt")
    private LocalDateTime modifyDt;

    @OneToMany(mappedBy = "board")
    private List<AttachFile> attachFileList = new ArrayList<>();
}
