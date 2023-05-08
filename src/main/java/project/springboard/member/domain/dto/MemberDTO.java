package project.springboard.member.domain.dto;

import lombok.*;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    private String msg ;



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

    public void addMsg() {
        if(this.status == MemberStatus.DEACTIVATE) {
            msg = "계정이 비활성화 되었습니다. 관리자에게 문의해주세요.";
        } else if(this.status  == MemberStatus.STANDBY) {
            msg = "승인이 완료되지 않았습니다. 관리자에게 문의해주세요.";
        }
    }


}
