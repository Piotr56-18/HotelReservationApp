package pl.application.ui.gui.guests;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.guest.GuestService;
import pl.application.domain.guest.dto.GuestDTO;
import pl.application.ui.gui.rooms.EditRoomScene;

import java.util.List;

public class GuestsTab {
    private Tab guestTab;
    private GuestService guestService = ObjectPool.getGuestService();
    private Stage primaryStage;

    public GuestsTab(Stage primaryStage){
        TableView<GuestDTO> tableView = getGuestDTOTableView();
        this.primaryStage = primaryStage;
        Button button = new Button("Dodaj nowego gościa");
        button.setOnAction(actionEvent -> {
            Stage stg = new Stage();
            stg.initModality(Modality.WINDOW_MODAL);
            stg.setScene(new AddNewGuestScene(stg,tableView).getMainScene());
            stg.initOwner(primaryStage);
            stg.setTitle("Dodaj nowego gościa");
            stg.showAndWait();

        });
        VBox layout = new VBox(button, tableView);

        this.guestTab = new Tab("Goście", layout);
        this.guestTab.setClosable(false);
    }

    private TableView<GuestDTO> getGuestDTOTableView() {
        TableView<GuestDTO> tableView = new TableView<>();

        TableColumn<GuestDTO,String> firstNamesColumn = new TableColumn<>("Imię");
        firstNamesColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<GuestDTO,String> lastNamesColumn = new TableColumn<>("Nazwisko");
        lastNamesColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<GuestDTO,Integer> ageColumn = new TableColumn<>("Wiek");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<GuestDTO,String> genderColumn = new TableColumn<>("Płeć");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<GuestDTO,GuestDTO>deleteColumn = new TableColumn<>("Usuń");
        deleteColumn.setCellValueFactory(value-> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>(){
            Button deleteButton = new Button("Usuń");
            Button editButton = new Button("Edytuj");
            HBox hBox = new HBox(deleteButton,editButton);

            @Override
            protected void updateItem(GuestDTO value, boolean empty){
                super.updateItem(value,empty);
                if(value==null){
                    setGraphic(null);
                }else{
                    setGraphic(hBox);
                    deleteButton.setOnAction(actionEvent -> {
                        guestService.removeGuest(value.getId());
                        tableView.getItems().remove(value);
                    });
                    editButton.setOnAction(actionEvent -> {
                        Stage stg = new Stage();
                        stg.initModality(Modality.WINDOW_MODAL);
                        stg.setScene(new EditGuestScene(stg,tableView,value).getMainScene());
                        stg.initOwner(primaryStage);
                        stg.setTitle("Edycja gościa");
                        stg.showAndWait();
                    });
                }
            }
        });

        tableView.getColumns().addAll(firstNamesColumn,lastNamesColumn,ageColumn,genderColumn,deleteColumn);

        List<GuestDTO> allAsDTO = guestService.getAllAsDTO();
        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    public Tab getGuestTab() {
        return guestTab;
    }
}
