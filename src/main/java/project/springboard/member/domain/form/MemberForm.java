package project.springboard.member.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
