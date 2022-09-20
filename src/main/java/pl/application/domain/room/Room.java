package pl.application.domain.room;

import pl.application.domain.room.dto.RoomDTO;

public class Room {
    private final int id;
    private final int number;
    private final BedType[] beds;

    Room(int id, int number, BedType[] bed) {
        this.id = id;
        this.number = number;
        this.beds = bed;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaje łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("%d Numer: %d %s", this.id, this.number, bedInfo);
    }
    String toCSV(){
        String[] bedsAsString = getBedsAsString();
        String  bedTypes = String.join("#",bedsAsString);
        return String.format("%d,%d,%s%s",this.id, this.number,bedTypes,System.getProperty("line.separator"));
    }

    private String[] getBedsAsString() {
        String[] bedsAsString = new String[this.beds.length];
        for (int i = 0; i < bedsAsString.length; i++) {
            bedsAsString[i] = beds[i].toString();
        }
        return bedsAsString;
    }

    public int getId() {
        return id;
    }

    public RoomDTO generateDTO() {
        String[] bedsAsString = getBedsAsString();
        String  bedTypes = String.join(",",bedsAsString);
        return new RoomDTO(this.id, this.number,bedTypes);
    }
}
