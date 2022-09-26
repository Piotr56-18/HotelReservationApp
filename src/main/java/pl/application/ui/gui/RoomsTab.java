package pl.application.ui.gui;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.room.RoomService;
import pl.application.domain.room.dto.RoomDTO;

import java.util.List;

public class RoomsTab {
    private Tab roomTab;
    private RoomService roomService = ObjectPool.getRoomService();

    public RoomsTab(Stage primaryStage) {
        TableView<RoomDTO> tableView = getRoomDTOTableView();

        Button button = new Button("Stwórz nowy");
        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.setScene(new AddNewRoomScene(stg,tableView).getMainScene());
            stg.initOwner(primaryStage);
            stg.setTitle("Dodawanie nowego pokoju");
            stg.showAndWait();

        });
        VBox layout = new VBox(button, tableView);

        this.roomTab = new Tab("Pokoje", layout);
        this.roomTab.setClosable(false);

    }

    private TableView<RoomDTO> getRoomDTOTableView() {
        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Numer");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Typy łóżek");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        TableColumn<RoomDTO, Integer> bedsCountColumn = new TableColumn<>("Ilość łóżek");
        bedsCountColumn.setCellValueFactory(new PropertyValueFactory<>("bedsCount"));

        TableColumn<RoomDTO, Integer> roomSizeColumn = new TableColumn<>("Rozmiar pokoju");
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("roomSize"));

        tableView.getColumns().addAll(numberColumn, roomSizeColumn, bedsCountColumn, bedsColumn);

        List<RoomDTO> allAsDTO = roomService.getAllAsDTO();
        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    Tab getRoomTab() {
        return roomTab;
    }
}
