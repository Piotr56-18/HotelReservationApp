package pl.application.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.guest.GuestService;
import pl.application.domain.guest.dto.GuestDTO;

import java.util.List;

public class GuestsTab {
    private Tab guestTab;
    private GuestService guestService = ObjectPool.getGuestService();

    public GuestsTab(Stage primaryStage){
        TableView<GuestDTO> tableView = getGuestDTOTableView();

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

        tableView.getColumns().addAll(firstNamesColumn,lastNamesColumn,ageColumn,genderColumn);

        List<GuestDTO> allAsDTO = guestService.getAllAsDTO();
        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    Tab getGuestTab() {
        return guestTab;
    }
}
