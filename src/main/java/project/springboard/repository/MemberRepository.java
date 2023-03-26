package project.springboard.repository;


import org.springframework.stereotype.Repository;
import project.springboard.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;
    public void save(Member memberEntity) {
        em.persist(memberEntity);

    }

    public Member findByMember(Long id) {
        Member member =  em.find(Member.class, id);
        return member;
    }

    public Optional<Member> findByMember(String loginId) {
        System.out.println("repository loginId = " + loginId);
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
}
