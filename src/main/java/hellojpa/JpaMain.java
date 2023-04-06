package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //JPA는 EntityManagerFactroy를 만들어야 한다. 데이터베이스 당 하나만 생성해서 애플리케이션 전체에서 공유

        EntityManager em = emf.createEntityManager(); //쓰레드간에 공유X, 사용하고 버려야 함(요청)

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            List<Member> result = em.createQuery("select m from Member as m", Member.class) //대상이 엔티티 객체(테이블X)
                    .setFirstResult(1)
                    .setMaxResults(10) //1~10
                    .getResultList();

            for(Member member : result){
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally { //예외처리가 중요하다
            em.close(); //닫아줘야 반환이 된다
        }

        em.close();
        emf.close();
    }
}
