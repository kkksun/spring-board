package project.springboard.member.repository;

public interface MemberCustomRepository {

    public Integer findDeletedLoginLid(String firstChar, String lastChr);
}
