package pl.application.ui.gui.reservations;

public class RoomSelectionItem {
    private final int number;
    private final int id;

    public RoomSelectionItem(int number, int id) {
        this.number = number;
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }
    public String toString(){
        return String.format("%d",this.number);
    }
}
