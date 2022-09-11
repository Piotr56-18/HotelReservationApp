package pl.application.domain.room;

public class Room {
    private int number;
    private BedType[] beds;

    public Room(int number, BedType[] bed) {
        this.number = number;
        this.beds = bed;
    }

    public String getInfo() {

        String bedInfo = "Rodzaje łóżek w pokoju:\n";
        for (BedType bed : beds) {
            bedInfo = bedInfo + "\t" + bed + "\n";
        }

        return String.format("Stworzono pokój o numerze: %d %s", this.number, bedInfo);
    }
}
