package project.springboard.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.springboard.board.domain.entity.Check;

import static project.springboard.member.domain.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Integer findDeletedLoginLid(String firstStr, String lastStr) {
        String likeStr = firstStr+ "%" + lastStr;

        Integer cnt = queryFactory.select(member.loginId.length())
                                  .from(member)
                                  .where(member.loginId.like(likeStr)
                                    .and(member.delCheck.eq(Check.Y)))
                                  .fetchOne();
        return cnt;

    };
}
