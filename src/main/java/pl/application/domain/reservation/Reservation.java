package pl.application.domain.reservation;

import pl.application.domain.guest.Guest;
import pl.application.domain.reservation.dto.ReservationDTO;
import pl.application.domain.room.Room;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Room room;
    @OneToOne
    private Guest guest;
    @Column(name = "fromDate")
    private LocalDateTime from;
    private LocalDateTime to;

    public Reservation() {
    }

    public Reservation(long id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    public Reservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    String toCSV(){
        return String.format("%s,%s,%s,%s,%s%s",
                this.id,
                this.room.getId(),
                this.guest.getId(),
                this.from.toString(),
                this.to.toString(),
                System.getProperty("line.separator"));
    }
    public long getId() {
        return id;
    }

    public ReservationDTO getAsDto() {
        return new ReservationDTO(this.id,this.from,this.to,
                this.room.getId(),this.room.getnumber(),
                this.guest.getId(),
                this.guest.getFirstName() + " " + this.guest.getLastName());
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

    public Room getRoom() {
        return this.room;
    }

    public LocalDateTime getTo() {
        return this.to;
    }
}
