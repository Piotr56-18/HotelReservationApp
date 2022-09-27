package pl.application.ui.gui;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import pl.application.ui.gui.guests.GuestsTab;
import pl.application.ui.gui.reservations.ReservationsTab;
import pl.application.ui.gui.rooms.RoomsTab;

public class MainTabView {
    private final TabPane mainTabs;

    public MainTabView(Stage primaryStage){
        this.mainTabs = new TabPane();

        ReservationsTab reservationsTab = new ReservationsTab(primaryStage);
        RoomsTab roomsTab = new RoomsTab(primaryStage);
        GuestsTab guestsTab = new GuestsTab(primaryStage);

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(),guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
