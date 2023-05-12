package project.springboard.member.domain.form;

import lombok.Data;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;

import java.time.LocalDateTime;

@Data
public class MemberForm {
    private Long id;

    private String loginId;

    private String userName;

    private String email;

    private MemberType type;

    private MemberStatus status;

    private LocalDateTime createDate;

    public MemberForm(MemberDTO member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.userName = member.getUserName();
        this.email = member.getEmail();
        this.type = member.getType();
        this.status = member.getStatus();
        this.createDate = member.getCreateDt();
    }
}
