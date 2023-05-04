package project.springboard.member.service;

import project.springboard.member.domain.dto.MemberDTO;

import java.util.List;

public interface MemberService {

     void saveMember(MemberDTO memberDTO);

     boolean loginIdDuplicateCheck(String loginId);

     List<MemberDTO> allMemberList();

     MemberDTO findMember(Long memberId);

     MemberDTO findMember(MemberDTO memberDTO);

     void saveAdmin();

     void editManageMember(Long memberId, MemberDTO editMEmber);
     void editMember(Long memberId, MemberDTO editMEmber);

     void deleteMember(Long memberId);
}
