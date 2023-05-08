package project.springboard.member.service;

import project.springboard.member.domain.dto.MemberDTO;
import project.springboard.member.domain.entity.MemberType;

import java.util.List;

public interface MemberService {

     void saveMember(MemberDTO memberDTO);

     boolean loginIdDuplicateCheck(String loginId);

     List<MemberDTO> allMemberList();

     MemberDTO findMember(Long memberId);

     MemberDTO findMember(MemberDTO memberDTO);

     void saveAdmin();

     void editMember(Long memberId, MemberDTO editMEmber, MemberType memberType);

     void deleteMember(Long memberId);
}
