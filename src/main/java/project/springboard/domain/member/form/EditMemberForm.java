package project.springboard.domain.member.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import project.springboard.domain.member.dto.MemberDTO;
import project.springboard.domain.member.entity.MemberStatus;
import project.springboard.domain.member.entity.MemberType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditMemberForm {

    @NotBlank
    private String loginId;

    @NotBlank
    @Length(min=8, max=12)
    private String password;

    @NotNull
    private Boolean pwChange;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotNull
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
