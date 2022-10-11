package pl.application.domain.guest;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class GuestJpaRepository implements GuestRepository{

    private static GuestRepository instance = new GuestJpaRepository(){};
    public static GuestRepository getInstance() {
        return instance;
    }

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static final EntityManager em = factory.createEntityManager();

    @Override
    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(firstName,lastName,age,gender);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(guest);
        transaction.commit();
        return guest;
    }

    @Override
    public void addExistingGuest(long id, String firstName, String lastName, int age, Gender gender) {

    }

    @Override
    public List<Guest> getAll() {
        return em.createQuery("SELECT a FROM Guest a",Guest.class).getResultList();
    }

    @Override
    public void saveAll() {
        System.out.println("Not implemented...");
    }

    @Override
    public void readAll() {
        System.out.println("Not implemented...");
    }

    @Override
    public void remove(long id) {
        Guest guestToBeRemoved = em.find(Guest.class,id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(guestToBeRemoved);
        transaction.commit();
    }

    @Override
    public void edit(long id, String firstName, String lastName, int age, Gender gender) {
        EntityTransaction transaction = em.getTransaction();
        Guest guestToBeModified = em.find(Guest.class, id);
        transaction.begin();
        guestToBeModified.setFirstName(firstName);
        guestToBeModified.setLastName(lastName);
        guestToBeModified.setAge(age);
        guestToBeModified.setGender(gender);
        transaction.commit();
    }

    @Override
    public Guest findById(long id) {
        return em.find(Guest.class,id);
    }
}
