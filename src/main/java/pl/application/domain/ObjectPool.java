package pl.application.domain;

import pl.application.domain.guest.GuestDatabaseRepository;
import pl.application.domain.guest.GuestRepository;
import pl.application.domain.guest.GuestService;
import pl.application.domain.reservation.ReservationDatabaseRepository;
import pl.application.domain.reservation.ReservationRepository;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.room.RoomDatabaseRepository;
import pl.application.domain.room.RoomRepository;
import pl.application.domain.room.RoomService;

public class ObjectPool {

    private static final RoomService roomService = new RoomService();
    private static final ReservationService reservationService = new ReservationService();

    private ObjectPool(){}

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
       // return GuestFileRepository.getInstance();
       return GuestDatabaseRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return roomService;
    }

    public static RoomRepository getRoomRepository() {
        //return RoomFileRepository.getInstance();
        return RoomDatabaseRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return reservationService;
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationDatabaseRepository.getInstance();
       // return ReservationFileRepository.getInstance();
    }
}
