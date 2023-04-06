package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //하나만 생성해서 애플리케이션 전체에서 공유

        EntityManager em = emf.createEntityManager(); //쓰레드간에 공유X, 사용하고 버려야 함

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //조회
            Member findMember = em.find(Member.class, 1L); // 1L -> pk
            //수정, em.persist 할 필요가 X, 커밋시점에서 감지 -> 자바 컬렉션처럼 동작
            findMember.setName("Jiyong");

            //삭제
            //em.remove(findMember);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close(); //예외처리가 중요하다
        }

        em.close();
        emf.close();
    }
}
