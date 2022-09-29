package pl.application.domain.room.dto;

public class RoomDTO {
    private final long id;
    private final int number;
    private final String beds;
    private final int bedsCount;
    private final int roomSize;

    public RoomDTO(long id, int number, String beds, int bedsCount, int roomSize) {
        this.id = id;
        this.number = number;
        this.beds = beds;
        this.bedsCount=bedsCount;
        this.roomSize=roomSize;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getBeds() {
        return beds;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public int getRoomSize() {
        return roomSize;
    }
}
