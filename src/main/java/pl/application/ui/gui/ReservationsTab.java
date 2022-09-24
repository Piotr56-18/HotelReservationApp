package pl.application.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.application.domain.ObjectPool;
import pl.application.domain.reservation.Reservation;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.reservation.dto.ReservationDTO;
import pl.application.domain.room.dto.RoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationsTab {
    private Tab reservationTab;
    private ReservationService reservationService = ObjectPool.getReservationService();

    public ReservationsTab(){
        TableView<ReservationDTO>tableView = new TableView<>();

        TableColumn<ReservationDTO, LocalDateTime> fromColumn = new TableColumn<>("Od");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<ReservationDTO, LocalDateTime> toColumn = new TableColumn<>("Do");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));

        TableColumn<ReservationDTO, Integer> roomColumn = new TableColumn<>("Pokój");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<ReservationDTO, String> guestColumn = new TableColumn<>("Rezerwujący");
        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        tableView.getColumns().addAll(fromColumn,toColumn,roomColumn,guestColumn);

        tableView.getItems().addAll(reservationService.getReservationsAsDTO());

        this.reservationTab = new Tab("Rezerwacje",tableView );
        this.reservationTab.setClosable(false);
    }


    Tab getReservationTab() {
        return reservationTab;
    }
}
