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
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String loginId;

    @NotBlank
    @Length(min=8, max=100)
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*,.\\/?]*$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    private String userName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[A-Za-z\\d@.]*$")
    private String email;

    @NotNull
    private MemberType type;

}