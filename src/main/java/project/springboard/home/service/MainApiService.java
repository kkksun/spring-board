package project.springboard.home.service;

import project.springboard.member.domain.dto.MemberDTO;

public interface MainApiService {

    boolean loginIdDuplicateCheck(String loginId);

    MemberDTO findMember(MemberDTO memberDTO);

    void saveMember(MemberDTO memberDTO);
}
