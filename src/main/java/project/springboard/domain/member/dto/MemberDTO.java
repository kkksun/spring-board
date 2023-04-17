package project.springboard.domain.member.dto;

import lombok.*;
import project.springboard.domain.member.entity.Member;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;
import project.springboard.domain.member.form.EditMemberForm;
import project.springboard.domain.member.form.JoinForm;
import project.springboard.domain.member.form.EditManageMemberForm;


import java.time.LocalDateTime;
import java.util.Optional;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDTO {
    private Long id;
    private String loginId;
    private String password;
    private String userName;
    private String email;
    private MemberType type;
    private MemberStatus status;
    private LocalDateTime createDt;
    private LocalDateTime modifyDt;


    public MemberDTO (EditManageMemberForm editMember) {
        this.loginId = editMember.getLoginId();
        if(editMember.getPwChange()) {
            this.password = editMember.getPassword();
        }
        this.userName = editMember.getUserName();
        this.email = editMember.getEmail();
        this.type = editMember.getType();
        this.status = editMember.getStatus();
    }

    public MemberDTO (EditMemberForm editMember) {
        this.loginId = editMember.getLoginId();
        if(editMember.getPwChange()) {
            this.password = editMember.getPassword();
        }
        this.userName = editMember.getUserName();
        this.email = editMember.getEmail();
        this.type = editMember.getType();
    }



    public static MemberDTO toMemberDTO(Optional<Member> member) {
        MemberDTO memberDTO = MemberDTO.builder()
                            .id(member.get().getId())
                            .loginId(member.get().getLoginId())
                            .password(member.get().getPassword())
                            .userName(member.get().getUserName())
                            .email(member.get().getEmail())
                            .type(member.get().getType())
                            .status(member.get().getStatus())
                            .createDt(member.get().getCreateDt())
                            .modifyDt(member.get().getModifyDt())
                            .build();
        return memberDTO;
    }
    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                            .id(member.getId())
                            .loginId(member.getLoginId())
                            .password(member.getPassword())
                            .userName(member.getUserName())
                            .email(member.getEmail())
                            .type(member.getType())
                            .status(member.getStatus())
                            .createDt(member.getCreateDt())
                            .modifyDt(member.getModifyDt())
                            .build();
        return memberDTO;
    }

    public static MemberDTO toMemberDTO(JoinForm member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .userName(member.getUserName())
                .email(member.getEmail())
                .type(member.getType())
                .status(MemberStatus.STANDBY)
                .build();
        return memberDTO;
    }

}
