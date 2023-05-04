package project.springboard.member.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.springboard.board.domain.entity.Check;
import project.springboard.member.domain.dto.MemberDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "LOGIN_ID", unique = true, nullable = false)
    private String loginId;

    private String password;

    @Column(name = "USER_NAME")
    private String userName;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_TYPE")
    private MemberType type;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEL_YN")
    private Check delCheck;

    @CreatedDate
    @Column(name = "CREATE_DT", updatable = false)
    private LocalDateTime createDt;

    @LastModifiedDate
    @Column(name = "MODIFY_DT")
    private LocalDateTime modifyDt;



    public static Member toMemberEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
//                .id(memberDTO.getId())
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .userName(memberDTO.getUserName())
                .email(memberDTO.getEmail())
                .type(memberDTO.getType())
                .status(memberDTO.getStatus())
                .build();

        return member;
    }

}
