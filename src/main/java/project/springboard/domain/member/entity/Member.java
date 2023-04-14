package project.springboard.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.springboard.domain.member.dto.MemberDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
//@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false)
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

    public Member() {

    }


    public static Member toMemberEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .id(memberDTO.getId())
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .userName(memberDTO.getUserName())
                .email(memberDTO.getEmail())
                .type(memberDTO.getType())
                .status(memberDTO.getStatus())
                .createDt(memberDTO.getCreateDt())
                .modifyDt(memberDTO.getModifyDt())
                .build();

        return member;
    }

}
