package project.springboard.service;

import project.springboard.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

public interface MemberService {

     void saveMember(MemberDTO memberDTO);

     boolean loginIdCheck(String loginId);

     List<MemberDTO> allMember();

     Optional<MemberDTO> findMember(Long memberId);

     Optional<MemberDTO> findMember(MemberDTO memberDTO);

     void adminSave();

}
