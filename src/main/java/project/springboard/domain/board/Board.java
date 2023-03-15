package project.springboard.domain.board;


import lombok.Getter;
import lombok.Setter;
import project.springboard.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;


    @Column(name = "user_id")
    private String userId;

    @Embedded
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn")
    private Check delCheck;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "modify_dt")
    private Date modifyDt;

}
