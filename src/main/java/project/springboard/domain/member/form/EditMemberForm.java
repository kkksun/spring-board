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
public class EditMemberForm {

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

    private MemberType type;



    public static EditMemberForm toEditForm(MemberDTO member) {
         EditMemberForm editMember = new EditMemberForm();
         editMember.setLoginId(member.getLoginId());
         editMember.setPassword(member.getPassword());
         editMember.setEmail(member.getEmail());
         editMember.setUserName(member.getUserName());
         editMember.setType(member.getType());
         editMember.setPwChange(false);
        return editMember;
    }


}
