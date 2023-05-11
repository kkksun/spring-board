package project.springboard.member.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import project.springboard.member.domain.entity.MemberType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MemberForm {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String loginId;

    @NotBlank
    @Length(min=8, max=100)
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*,.\\/?]*$")
    private String password;

    @NotBlank
    @Length(min=8, max=100)
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*,.\\/?]*$")
    private String passwordConfirm;

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
