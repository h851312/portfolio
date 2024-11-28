package jongwoo.shop.member;

import org.springframework.data.jpa.repository.JpaRepository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long id);

    Member findByUsername(String username);
    Member findByEmail(String email);
//    Member findById(Long id);
    List<Member> findAll();
    }


//
//    Optional<Member> findByEmail(String email);
//    @Modifying
//    @Query(value = "UPDATE Member m set m.username = :name WHERE m.id =: id ", nativeQuery = false)
//    String update(@Param("username") String username, @Param("member_id") Long id);


//    private final EntityManager em;
//
//
//    @Transactional
//    public void saveMember(Member member){
//        em.persist(member);
//    }
//
//    public Member findOne(Long id){
//        return em.find(Member.class, id);
//    }
//
//    public List<Member> findAll(){
//        return em.createQuery("select m from Member m")
//                .getResultList();
//    }
//
//    public List<Member> findByName(String username) {
//        return em.createQuery("select m from Member m where m.username = :username", Member.class)
//                .setParameter("username", username)
//                .getResultList();
//    }
