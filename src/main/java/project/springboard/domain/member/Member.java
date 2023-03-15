package project.springboard.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    private String password;

    @Column(name = "user_name")
    private String userName;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @Column(name = "modify_dt")
    private LocalDateTime modifyDt;


}
