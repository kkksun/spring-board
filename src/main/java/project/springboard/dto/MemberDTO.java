package project.springboard.dto;

import lombok.*;
import project.springboard.domain.member.Member;
import project.springboard.domain.member.MemberStatus;
import project.springboard.domain.member.MemberType;

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
    private String selectType;
    private MemberType type;
    private MemberStatus status;
    private LocalDateTime createDt;
    private LocalDateTime modifyDt;

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


}
