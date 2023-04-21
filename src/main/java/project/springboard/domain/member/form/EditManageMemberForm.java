package project.springboard.domain.member.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class EditManageMemberForm {

    @NotBlank
    private String loginId;

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

    @NotNull
    private MemberType type;

    @NotNull
    private MemberStatus status;

     public static EditManageMemberForm toEditForm(MemberDTO member) {
         EditManageMemberForm editMember = new EditManageMemberForm();
         editMember.setLoginId(member.getLoginId());
         editMember.setPassword(member.getPassword());
         editMember.setType(member.getType());
         editMember.setEmail(member.getEmail());
         editMember.setUserName(member.getUserName());
         editMember.setStatus(member.getStatus());
         editMember.setPwChange(false);
        return editMember;
    }


}
