package pl.application.domain.room;
import javax.persistence.*;
import java.util.List;

public class RoomJpaRepository implements RoomRepository{

    private static RoomRepository instance = new RoomJpaRepository(){};
    public static RoomRepository getInstance() {
        return instance;
    }

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static final EntityManager em = factory.createEntityManager();

    @Override
    public void saveAll() {

    }

    @Override
    public void readAll() {

    }

    @Override
    public void remove(long id) {
        EntityTransaction transaction = em.getTransaction();
        Room roomToBeRemoved = em.find(Room.class, id);
        transaction.begin();
        em.remove(roomToBeRemoved);
        transaction.commit();
    }

    @Override
    public void edit(long id, int number, List<BedType> bedTypes) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Room roomToBeModified = em.find(Room.class, id);
        roomToBeModified.setBeds(bedTypes);
        roomToBeModified.setNumber(number);
        transaction.commit();
    }

    @Override
    public Room getById(long id) {
        return em.find(Room.class,id);
    }

    @Override
    public Room createNewRoom(int number, List<BedType> bedTypes) {
        Room room = new Room(number, bedTypes);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(room);
        transaction.commit();
        return room;
    }

    @Override
    public List<Room> getAllRooms() {
        return em.createQuery("SELECT a FROM Room a",Room.class).getResultList();
    }
}
