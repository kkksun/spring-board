package project.springboard.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.springboard.dto.MemberDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
//@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    private String password;

    @Column(name = "user_name")
    private String userName;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "modify_dt")
    private LocalDateTime modifyDt;


    public static Member toMemberEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.loginId = memberDTO.getLoginId();
        member.userName = memberDTO.getUserName();
        member.password = memberDTO.getPassword();
        member.email = memberDTO.getEmail();
        member.status = memberDTO.getStatus();
        member.type = memberDTO.getType();
        return member;
    }

}
