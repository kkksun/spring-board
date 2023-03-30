package project.springboard.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveMember(Member memberEntity) {
        em.persist(memberEntity);

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
    public List<Member> allMember() {
        return  em.createQuery("select m from Member m", Member.class)
                                .getResultList();
    }
}
