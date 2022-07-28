package hellojpa.값타입.값타입컬렉션;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CollectionValueMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("COLLECTION_MAIN");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            EmbeddedMember member = new EmbeddedMember();
            member.setName("MEMBER");
            member.setHomeAddress(new Address("city1", "street", "1000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");
            /*
            member.getAddressHistory().add(new Address("old city1", "street", "1000"));
            member.getAddressHistory().add(new Address("old city2", "street", "1000"));
            */

            member.getAddressHistory().add(new AddressEntity("old city1", "street", "1000"));
            member.getAddressHistory().add(new AddressEntity("old city2", "street", "1000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================== START ==================");
            EmbeddedMember findMember = em.find(EmbeddedMember.class, member.getId());

            System.out.println("================== MOD START ==================");
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("NEW CITY!", oldAddress.getStreet(), oldAddress.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");


            findMember.getAddressHistory().remove(new AddressEntity("old city1", "street", "1000"));
            findMember.getAddressHistory().add(new AddressEntity("WELCOME CITY!", "street", "1000"));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
