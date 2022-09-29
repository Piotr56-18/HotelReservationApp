package pl.application;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.application.domain.ObjectPool;
import pl.application.domain.guest.GuestService;
import pl.application.domain.reservation.ReservationService;
import pl.application.domain.room.RoomService;
import pl.application.exceptions.PersistenceToFileException;
import pl.application.ui.gui.PrimaryStage;
import pl.application.ui.text.TextUI;
import pl.application.util.SystemUtils;

import java.io.IOException;

public class App extends Application {

    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();

    public static void main(String[] args) {

        try {
            SystemUtils su = new SystemUtils();
            su.createDatabaseConnection();
            SystemUtils.createDataDirectory();
            System.out.println("Trwa wczytywanie danych...");
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
        } catch (IOException e) {
            throw new PersistenceToFileException(SystemUtils.DATA_DIRECTORY.toString(), "create data directory","directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }
    @Override
    public void start(Stage primaryStage){
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }
    @Override
    public void stop(){
        System.out.println("Koniec programu. Zapis danych.");
        guestService.saveAll();
        roomService.saveAll();
        reservationService.saveAll();
    }
}


