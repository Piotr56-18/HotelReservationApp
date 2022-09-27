package pl.application.ui.gui.reservations;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationsTab {
    private final Tab reservationTab;
    private final ReservationService reservationService = ObjectPool.getReservationService();

    public ReservationsTab(Stage primaryStage){
        TableView<ReservationDTO> tableView = getReservationDTOTableView();

        Button button = new Button("Stwórz rezerwację");
        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.setScene(new AddNewReservationScene(stg,tableView).getMainScene());
            stg.initOwner(primaryStage);
            stg.setTitle("Stwórz rezerwację");
            stg.showAndWait();

        });
        VBox layout = new VBox(button, tableView);

        this.reservationTab = new Tab("Rezerwacje",layout );
        this.reservationTab.setClosable(false);
    }

    private TableView<ReservationDTO> getReservationDTOTableView() {
        TableView<ReservationDTO>tableView = new TableView<>();

        TableColumn<ReservationDTO, LocalDateTime> fromColumn = new TableColumn<>("Od");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<ReservationDTO, LocalDateTime> toColumn = new TableColumn<>("Do");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));

        TableColumn<ReservationDTO, Integer> roomColumn = new TableColumn<>("Pokój");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<ReservationDTO, String> guestColumn = new TableColumn<>("Rezerwujący");
        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        TableColumn<ReservationDTO,ReservationDTO>deleteColumn = new TableColumn<>("Usuń");
        deleteColumn.setCellValueFactory(value-> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>(){
            final Button deleteButton = new Button("Usuń");
            @Override
            protected void updateItem(ReservationDTO value, boolean empty){
                super.updateItem(value,empty);
                if(value==null){
                    setGraphic(null);
                }else{
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(actionEvent -> {
                        reservationService.removeReservation(value.getId());
                        tableView.getItems().remove(value);
                    });
                }
            }
        });

        tableView.getColumns().addAll(fromColumn,toColumn,roomColumn,guestColumn,deleteColumn);

        tableView.getItems().addAll(reservationService.getReservationsAsDTO());
        return tableView;
    }


    public  Tab getReservationTab() {
        return reservationTab;
    }
}
