package project.springboard.service;

import project.springboard.domain.member.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

public interface MemberService {

     void saveMember(MemberDTO memberDTO);

     boolean loginIdDuplicateCheck(String loginId);

     List<MemberDTO> allMember();

     MemberDTO findMember(Long memberId);

     MemberDTO findMember(MemberDTO memberDTO);

     void adminSave();

     void editManageMember(Long memberId, MemberDTO editMEmber);
     void editMember(Long memberId, MemberDTO editMEmber);

     void deleteMember(Long memberId);
}
