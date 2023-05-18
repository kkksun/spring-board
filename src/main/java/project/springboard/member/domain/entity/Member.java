package project.springboard.member.domain.entity;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Auditable<Long>  {

//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "USER_ID")
//    private Long id;

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

//    @OneToMany(mappedBy = "createdBy")
//    List<Member> createdMemberList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "modifiedBy")
//    List<Member> midifiedMemberList = new ArrayList<>();



    public static Member toMemberEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
//                .id(memberDTO.getId())
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .userName(memberDTO.getUserName())
                .email(memberDTO.getEmail())
                .type(memberDTO.getType())
                .status(memberDTO.getStatus())
                .delCheck(Check.N)
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

    public void deleteMember() {
        String loginId = this.loginId;
        this.loginId = loginId.charAt(0) + "***"+ loginId.charAt(loginId.length()-1);
        this.userName = "*****";
        this.email = "*****";
        this.delCheck = Check.Y;
        this.status = MemberStatus.DELETE;
    }
}
