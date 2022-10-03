package pl.application.domain.reservation;

import pl.application.domain.ObjectPool;
import pl.application.domain.guest.Guest;
import pl.application.domain.guest.GuestService;
import pl.application.domain.room.Room;
import pl.application.domain.room.RoomService;
import pl.application.util.SystemUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationDatabaseRepository implements ReservationRepository{
    private final static ReservationRepository instance = new ReservationDatabaseRepository();
    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private List<Reservation> reservations = new ArrayList<>();

    public static ReservationRepository getInstance(){
        return instance;
    }


    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        try {
            String fromAsString = from.format(DateTimeFormatter.ISO_DATE_TIME);   //yyyy-MM-dd HH:mm:ss           - formay daty
            String toAsString = from.format(DateTimeFormatter.ISO_DATE_TIME);   //yyyy-MM-dd HH:mm:ss
            Statement statement = SystemUtils.connection.createStatement();
            String createTemplate = "INSERT INTO RESERVATIONS(ROOM_ID, GUEST_ID, RES_FROM, RES_TO) VALUES(%d, %d, '%s', '%s')";
            String createQuery = String.format(createTemplate,room.getId(),guest.getId(),fromAsString,toAsString);
            statement.execute(createQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            long id = -1;
            while (rs.next()){
                id = rs.getLong(1);
            }
            Reservation newReservation = new Reservation(id,room,guest,from,to);
            this.reservations.add(newReservation);
            statement.close();
            return newReservation;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd przy tworzeniu rezerwacji");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readAll() {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM RESERVATIONS");
            while (rs.next()){
                long id = rs.getLong(1);
                long roomId = rs.getLong(2);
                long guestId = rs.getLong(3);
                String fromAsString = rs.getString(4);
                String toAsString = rs.getString(5);

                LocalDateTime from = LocalDateTime.parse(fromAsString.replace(" ", "T"),DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime to = LocalDateTime.parse(toAsString.replace(" ","T"),DateTimeFormatter.ISO_DATE_TIME);

                this.reservations.add(new Reservation(id,this.roomService.getRoomById(roomId),this.guestService.getGuestById(guestId),from,to));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd przy odczycie rezerwacji");
            throw  new RuntimeException(e);
        }

    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Reservation> getAll() {
        return this.reservations;
    }

    @Override
    public void remove(long id) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String removeTemplate = "DELETE FROM RESERVATIONS WHERE ID=%d";
            String removeQuery = String.format(removeTemplate,id);
            statement.execute(removeQuery);
            statement.close();

            this.removeById(id);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd przy usuwaniu rezerwacji");
            throw new RuntimeException(e);
        }

    }

    private void removeById(long id) {
        int indecToBeRemoved = -1;
        for(int i = 0;i<this.reservations.size();i++){
            if(this.reservations.get(i).getId()==i){
                indecToBeRemoved = i;
                this.reservations.remove(indecToBeRemoved);
                break;
            }
        }
    }
}
