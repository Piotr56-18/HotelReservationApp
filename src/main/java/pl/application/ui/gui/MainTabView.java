package pl.application.ui.gui;
import javafx.scene.control.TabPane;

public class MainTabView {
    private TabPane mainTabs;

    public MainTabView(){
        this.mainTabs = new TabPane();

        ReservationsTab reservationsTab = new ReservationsTab();
        RoomsTab roomsTab = new RoomsTab();
        GuestsTab guestsTab = new GuestsTab();

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(),guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
