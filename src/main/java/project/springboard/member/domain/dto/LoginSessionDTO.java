package project.springboard.member.domain.dto;

import lombok.Data;
import project.springboard.member.domain.entity.MemberType;

@Data
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
