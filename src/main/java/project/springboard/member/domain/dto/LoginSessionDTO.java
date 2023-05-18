package project.springboard.member.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.springboard.member.domain.entity.MemberType;

@Data
@NoArgsConstructor
public class LoginSessionDTO {
    private Long id;
    private String loginId;
    private MemberType type;


    public LoginSessionDTO(MemberDTO member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.type = member.getType();
    }
}
