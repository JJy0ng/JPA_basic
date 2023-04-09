package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //JPA는 EntityManagerFactory를 만들어야 한다. 데이터베이스 당 하나만 생성해서 애플리케이션 전체에서 공유

        EntityManager em = emf.createEntityManager(); //쓰레드간에 공유X, 사용하고 버려야 함(요청)

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJpa");

            //영속
            System.out.println("=== BEFORE ===");
            em.persist(member); //엔티티매니저 안에 있는 영속성 컨텍스트 통해 멤버 객체가 관리가 된다, db에 저장x
            //em.detach(member); 멤버를 영속성 컨텍스트에서 분리, 준영속 상태
            System.out.println("=== AFTER ===");

            tx.commit(); //여기서 db에 쿼리가 나감
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        em.close();
        emf.close();
    }
}
