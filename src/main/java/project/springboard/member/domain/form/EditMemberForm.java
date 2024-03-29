package project.springboard.member.domain.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.Member;
import project.springboard.member.domain.entity.MemberStatus;
import project.springboard.member.domain.entity.MemberType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class EditMemberForm {

    @NotBlank
    @Length(min=8, max=100)
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*,.\\/?]*$")
    private String password;

    @NotNull
    private Boolean pwChange;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    private String userName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[A-Za-z\\d@.]*$")
    private String email;

    private MemberType type;

    private MemberStatus status;

    private RequestedPage requestedPage;
}
