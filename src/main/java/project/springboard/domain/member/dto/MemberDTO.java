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
@ToString
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


    // 생성자? static? 어느게 더 나은 방법인지 생각해보기,
    // DTO 하나로 member 관련된 form을 다 받는게 맞는지 더 생각해보기
    @Builder
    public MemberDTO (Long id, String loginId, String password, String userName, String email, MemberType type, MemberStatus status, LocalDateTime createDt, LocalDateTime modifyDt) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.type = type;
        this.status = status;
        this.createDt = createDt;
        this.modifyDt =modifyDt;

    }

    public MemberDTO (EditManageMemberForm editMember) {
        this.loginId = editMember.getLoginId();
        if(editMember.getPwChange()) {
            this.password = this.getPassword();
        }
        this.userName = editMember.getUserName();
        this.email = editMember.getEmail();
        this.type = editMember.getType();
        this.status = editMember.getStatus();
        this.modifyDt = LocalDateTime.now();
    }

    public MemberDTO (EditMemberForm editMember) {
        this.loginId = editMember.getLoginId();
        if(editMember.getPwChange()) {
            this.password = this.getPassword();
        }
        this.userName = editMember.getUserName();
        this.email = editMember.getEmail();
        this.type = editMember.getType();
        this.modifyDt = LocalDateTime.now();
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
