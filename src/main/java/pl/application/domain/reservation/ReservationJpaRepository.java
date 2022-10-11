package pl.application.domain.reservation;

import org.h2.mvstore.tx.Transaction;
import pl.application.domain.guest.Guest;
import pl.application.domain.room.Room;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationJpaRepository implements ReservationRepository{

    private static ReservationRepository instance = new ReservationJpaRepository(){};
    public static ReservationRepository getInstance() {
        return instance;
    }

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static final EntityManager em = factory.createEntityManager();

    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation reservation = new Reservation(room,guest,from,to);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(reservation);
        transaction.commit();
        return null;
    }

    @Override
    public void readAll() {

    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Reservation> getAll() {
        return em.createQuery("Select a FROM Reservation a", Reservation.class).getResultList();
    }

    @Override
    public void remove(long id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Reservation reservationToBeRemoved = em.find(Reservation.class, id);
        em.remove(reservationToBeRemoved);
        transaction.commit();
    }
}
