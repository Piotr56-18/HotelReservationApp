package pl.application.domain;

import pl.application.domain.guest.GuestRepository;
import pl.application.domain.guest.GuestService;
import pl.application.domain.reservation.ReservationRepository;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.room.RoomRepository;
import pl.application.domain.room.RoomService;

public class ObjectPool {
    private ObjectPool(){};

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
        return GuestRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
        return RoomRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }
}
