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
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*,.\\/?]*$")
    private String password;


}
