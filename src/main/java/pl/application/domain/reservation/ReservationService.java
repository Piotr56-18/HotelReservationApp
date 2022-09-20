package pl.application.domain.reservation;

import pl.application.domain.guest.Guest;
import pl.application.domain.guest.GuestService;
import pl.application.domain.room.Room;
import pl.application.domain.room.RoomService;
import pl.application.util.Properties;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationService {
    private final RoomService roomService = new RoomService();
    private final GuestService guestService = new GuestService();
    private final ReservationRepository repository = new ReservationRepository();

    public Reservation createNewReservation(LocalDate from, LocalDate to, int roomId, int guestaId) throws IllegalArgumentException {

        //TODO: handle null room
        Room room = this.roomService.getRoomById(roomId);
        //TODO: handle null guest
        Guest guest = this.guestService.getGuestById(guestaId);

        LocalDateTime fromWithTime = from.atTime(Properties.HOTEL_NIGHT_START_HOUR,Properties.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(Properties.HOTEL_NIGHT_END_HOUR,Properties.HOTEL_NIGHT_END_MINUTE);

        if(toWithTime.isBefore(fromWithTime)){
            throw new IllegalArgumentException();
        }

        return this.repository.createNewReservation(room,guest,fromWithTime,toWithTime);
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void saveAll() {
        this.repository.saveAll();
    }
}
