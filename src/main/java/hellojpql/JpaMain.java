package hellojpql;

import hellojpql.jpql.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static hellojpql.jpql.MemberType.ADMIN;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();  //트랜잭션 시작

        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setType(ADMIN);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setType(ADMIN);

            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            String jpql = "select group_concat(m.username) from Member m";

            List<String> query = em.createQuery(jpql, String.class)
                            .getResultList();

            for (String s : query) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
