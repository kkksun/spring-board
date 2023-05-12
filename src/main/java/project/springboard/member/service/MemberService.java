package project.springboard.member.service;

import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberType;
import project.springboard.member.domain.form.RequestedPage;

import java.util.List;

public interface MemberService {

     void saveMember(MemberDTO memberDTO);

     boolean loginIdDuplicateCheck(String loginId);

     List<MemberDTO> allMemberList();

     MemberDTO findMember(Long memberId);

     MemberDTO findMember(MemberDTO memberDTO);

     void saveAdmin();

     void editMember(Long memberId, MemberDTO editMEmber, RequestedPage requestedPage);

     void deleteMember(Long memberId);
}
