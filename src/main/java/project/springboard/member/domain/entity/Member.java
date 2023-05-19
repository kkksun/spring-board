package project.springboard.member.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.springboard.board.domain.entity.Check;
import project.springboard.board.domain.entity.Comment;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.form.RequestedPage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Auditable<Long>  {

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
    @ColumnDefault("'N'")
    private Check delCheck;


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

    public void updateMember(MemberDTO editMember, RequestedPage requestedPage) {
        this.email = editMember.getEmail();
        this.userName = editMember.getUserName();
        if(editMember.getPassword() != null) {
            this.password = editMember.getPassword();
        }
        if(requestedPage == RequestedPage.MANAGE) {
            this.type = editMember.getType();
            this.status = editMember.getStatus();
        }
    }

    public void deleteMember(Integer loginIdLength) {
        this.userName = "*****";
        this.email = "*****";
        this.delCheck = Check.Y;
        this.status = MemberStatus.DELETE;

        String masking = "";
        if(loginIdLength != 0) {
            for(int i = 1; i < loginIdLength; i++) {
                masking += "*";
            }
        }else {
            masking = "***";
        }
        this.loginId = loginId.substring(0,1) + masking + loginId.substring(loginId.length() - 1);

    }

//    @PrePersist
//    public void setCreatedBy(){
//      this.createdBy = this;
//      this.modifiedBy = this;
//    }

}
