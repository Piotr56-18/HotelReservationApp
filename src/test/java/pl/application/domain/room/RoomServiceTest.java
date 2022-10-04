package pl.application.domain.room;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.application.domain.ObjectPool;
import pl.application.domain.guest.Gender;
import pl.application.domain.guest.Guest;
import pl.application.domain.reservation.Reservation;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.room.BedType;
import pl.application.domain.room.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Test
    public void convertFromIntOptionsIntoBedTypesTest() {
        //given
        RoomService roomService = ObjectPool.getRoomService();
        int[] bedTypeOptions = new int[]{1, 2, 3};
        //when
        List<BedType> bedTypes = roomService.getBedTypes(bedTypeOptions);
        //then
        assertEquals(3, bedTypes.size());
        assertEquals(BedType.SINGLE, bedTypes.get(0));
        assertEquals(BedType.DOUBLE, bedTypes.get(1));
        assertEquals(BedType.KING_SIZE, bedTypes.get(2));
    }

    @Test
    public void getAvailableRoomsShouldThrowExceptionWhenNullParam() {
        //given
        RoomService roomService = new RoomService();
        //then
        assertThrows(IllegalArgumentException.class, () -> roomService.getAvailableRooms(null, null));
    }

    @Test
    public void getAvailableRoomsShouldThrowExceptionWhenToDateIsBeforeFrom() {
        //given
        RoomService roomService = new RoomService();
        LocalDate from = LocalDate.parse("2022-12-03");
        LocalDate to = LocalDate.parse("2022-11-03");
        //when, then
        assertThrows(IllegalArgumentException.class, ()->{
            roomService.getAvailableRooms(from,to);
        });
    }
    @Test
    public void getAvailableRoomsShouldReturnEmptyListWhenNoRoomsInSystem(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        when(roomRepository.getAllRooms()).thenReturn(new ArrayList<>());
        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        LocalDate from = LocalDate.parse("2022-11-03");
        LocalDate to = LocalDate.parse("2022-12-03");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(0,availableRooms.size());
    }
    @Test
    public void getAvailableRoomsShouldReturnAllRoomsWhenNoReservations(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room room = new Room(1,21,null);
        List<Room>rooms = new ArrayList<>();
        rooms.add(room);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.getAllReservations()).thenReturn(new ArrayList<>());

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-11-03");
        LocalDate to = LocalDate.parse("2022-12-03");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
    }
    @Test
    public void getAvailableRoomShouldRemoveRoomIfReservationStartDateIsTheSame(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-11-03T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-03T09:00");
        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-11-03");
        LocalDate to = LocalDate.parse("2022-12-03");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
    @Test
    public void getAvailableRoomShouldRemoveRoomIfReservationEndDateIsTheSame(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-11-03T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-03T10:00");
        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-11-04");
        LocalDate to = LocalDate.parse("2022-12-03");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
    @Test
    public void getAvailableRoomShouldRemoveRoomIfReservationStartDateIsBeetwen(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-03T10:00");
        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-11-20");
        LocalDate to = LocalDate.parse("2022-12-05");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
    @Test
    public void getAvailableRoomShouldRemoveRoomIfReservationEndDateIsBeetwen(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-03T10:00");
        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-12-02");
        LocalDate to = LocalDate.parse("2022-12-05");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
    @Test
    public void getAvailableRoomShouldRemoveRoomIfReservationIsBeetwen(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-10T10:00");
        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-12-03");
        LocalDate to = LocalDate.parse("2022-12-05");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
    @Test
    public void getAvailableRoomsTest(){
        //given
        RoomRepository  roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1,21,null);
        Room availableRoom = new Room(2,211,null);
        Room room = new Room(3,200,null);

        List<Room>rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        rooms.add(room);
        when(roomRepository.getAllRooms()).thenReturn(rooms);

        Guest guest = new Guest(1, "Piotr", "Kowal", 22, Gender.MALE);
        LocalDateTime fromReserved  = LocalDateTime.parse("2022-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2022-12-10T10:00");

        LocalDateTime fromReserved2  = LocalDateTime.parse("2022-12-05T15:00");
        LocalDateTime toReserved2 = LocalDateTime.parse("2022-12-20T10:00");

        Reservation newReservation = new Reservation(1,reservedRoom,guest,fromReserved,toReserved);
        Reservation newReservation2 = new Reservation(1,room,guest,fromReserved2,toReserved2);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation>reservations = new ArrayList<>();
        reservations.add(newReservation);
        reservations.add(newReservation2);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);
        LocalDate from = LocalDate.parse("2022-12-03");
        LocalDate to = LocalDate.parse("2022-12-06");
        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);
        //then
        assertEquals(1,availableRooms.size());
        assertEquals(211,availableRooms.get(0).getnumber());
    }
}
