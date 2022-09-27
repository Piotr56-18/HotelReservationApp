package pl.application.ui.gui.guests;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.guest.GuestService;
import pl.application.domain.guest.dto.GuestDTO;
import pl.application.util.Properties;

import java.util.List;

public class EditGuestScene {

    private Scene mainScene;
    private GuestService guestService = ObjectPool.getGuestService();

    public EditGuestScene(Stage stg, TableView<GuestDTO> tableView, GuestDTO guest) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);

        Label firstNameLabel = new Label("Imię:");
        TextField firstNameField = new TextField();
        firstNameField.setText(guest.getFirstName());

        firstNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\p{L}*")){
                firstNameField.setText(oldValue);
            }
        });

        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);

        Label lastNameLabel = new Label("Nazwisko:");
        TextField lastNameField = new TextField();
        lastNameField.setText(guest.getLastName());

        lastNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\p{L}*")){
                lastNameField.setText(oldValue);
            }
        });

        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);

        Label ageLabel = new Label("Wiek:");
        TextField ageField = new TextField();
        ageField.setText(String.valueOf(guest.getAge()));

        ageField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                ageField.setText(oldValue);
            }
        });

        gridPane.add(ageLabel, 0, 2);
        gridPane.add(ageField, 1, 2);

        Label genderLabel = new Label("Płeć:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll(Properties.MALE, Properties.FEMALE);
        genderField.setValue(guest.getGender());
        gridPane.add(genderLabel, 0, 3);
        gridPane.add(genderField, 1, 3);

        Button editGuestButton = new Button("Edytuj gościa");
        editGuestButton.setOnAction(actionEvent -> {
            String newGuestFirstName = firstNameField.getText();
            String newGuestLastName = lastNameField.getText();
            int newGuestAge = Integer.parseInt(ageField.getText());
            String newGuestGender = genderField.getValue();
            boolean isMale = false;
            if(newGuestGender.equals(Properties.MALE)){
                isMale = true;
            }
            this.guestService.editGuest(guest.getId(),newGuestFirstName, newGuestLastName, newGuestAge, isMale);
            tableView.getItems().clear();
            List<GuestDTO> allAsDTO = guestService.getAllAsDTO();
            tableView.getItems().addAll(allAsDTO);
            stg.close();
        });

        editGuestButton.setPadding(new Insets(5,5,5,5));
        gridPane.add(editGuestButton,0,4);

        this.mainScene = new Scene(gridPane, 740, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader().getResource("hotelReservation.css").toExternalForm());
    }


    public Scene getMainScene() {
        return this.mainScene;
    }
}
