package project.springboard.member.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import project.springboard.board.domain.entity.Check;
import project.springboard.global.auditing.Auditable;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.form.RequestedPage;

import javax.persistence.*;


@Entity
@Getter @Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Auditable<Long>  {

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

    @Transient
    private String role;

    public static Member toMemberEntity(MemberDTO memberDTO) {
        return Member.builder()
//                .id(memberDTO.getId())
                     .loginId(memberDTO.getLoginId())
                     .password(memberDTO.getPassword())
                     .userName(memberDTO.getUserName())
                     .email(memberDTO.getEmail())
                     .type(memberDTO.getType())
                     .status(memberDTO.getStatus())
                     .build();
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
        if(loginIdLength != null) {
            for(int i = 1; i < loginIdLength; i++) {
                masking += "*";
            }
        }else {
            masking = "***";
        }
        this.loginId = loginId.substring(0,1) + masking + loginId.substring(loginId.length() - 1);

    }

}
