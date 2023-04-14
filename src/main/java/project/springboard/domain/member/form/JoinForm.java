package project.springboard.domain.member.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import project.springboard.domain.member.entity.MemberType;

import javax.validation.constraints.*;

@Data
public class JoinForm {

    @NotBlank
    private String loginId;

    @NotBlank
    @Length(min=8, max=12)
    private String password;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private MemberType type;

}
