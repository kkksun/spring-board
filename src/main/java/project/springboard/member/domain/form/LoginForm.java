package project.springboard.member.domain.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter @Setter
public class LoginForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
