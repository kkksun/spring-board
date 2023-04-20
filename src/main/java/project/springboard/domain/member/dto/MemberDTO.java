package project.springboard.domain.member.dto;

import lombok.*;
import project.springboard.domain.member.entity.Member;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;

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
    private Map<String, String> msg ;


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

    public void addMsg() {
        this.msg = new HashMap<>();
        if(this.status == MemberStatus.DEACTIVATE) {
            msg.put("deactivate", "계정이 비활성화 되었습니다. 관리자에게 문의해주세요.");
        } else if(this.status  == MemberStatus.STANDBY) {
            msg.put("unapproved", "승인이 완료되지 않았습니다. 관리자에게 문의해주세요.");
        }
    }


}
