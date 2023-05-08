package project.springboard.member.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.member.domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberJpaRepository {


    private final EntityManager em;

    public void saveMember(Member member) {
        em.persist(member);

    }

    public Member findByMember(Long id) {
        return  em.find(Member.class, id);
    }

    public Optional<Member> findByMember(String loginId) {
        Optional<Member> member = null;
        try {
            member = Optional.ofNullable(em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                    .setParameter("loginId", loginId)
                    .getSingleResult());
        } catch (NoResultException e) {
            member = Optional.empty();
        } finally {
            return member;
        }
    }

    @Override
    public List<Member> allMemberList() {
        return  em.createQuery("select m from Member m", Member.class)
                                .getResultList();

    }



    @Override
    public Member deleteMember(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
