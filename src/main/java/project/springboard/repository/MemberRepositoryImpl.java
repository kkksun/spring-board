package project.springboard.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.springboard.domain.member.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
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
    public List<Member> allMember() {
        return  em.createQuery("select m from Member m", Member.class)
                                .getResultList();

    }

    @Override
    public void manageMemberEdit(Long memberId, Member editMEmber) {
        Member member = em.find(Member.class, memberId);
        if(editMEmber.getPassword() != null) {
            member.setPassword(editMEmber.getPassword());
        }
        member.setEmail(editMEmber.getEmail());
        member.setUserName(editMEmber.getUserName());
        member.setType(editMEmber.getType());
        member.setStatus(editMEmber.getStatus());
        member.setModifyDt(editMEmber.getModifyDt());
    }

    @Override
    public void memberEdit(Long memberId, Member editMEmber) {
        Member member = em.find(Member.class, memberId);
        if(editMEmber.getPassword() != null) {
            member.setPassword(editMEmber.getPassword());
        }
        member.setEmail(editMEmber.getEmail());
        member.setUserName(editMEmber.getUserName());
        member.setModifyDt(editMEmber.getModifyDt());
    }

    @Override
    public void deleteMember(Long memberId) {
        Member deleteMember = em.find(Member.class, memberId);
        em.remove(deleteMember);
    }
}
