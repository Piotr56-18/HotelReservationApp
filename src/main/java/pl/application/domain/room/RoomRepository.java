package pl.application.domain.room;

import pl.application.domain.guest.Gender;
import pl.application.domain.guest.Guest;
import pl.application.exceptions.PersistenceToFileException;
import pl.application.util.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int number, BedType[] bedTypes) {
        Room room = new Room(findNewId(), number, bedTypes);
        rooms.add(room);
        return room;
    }
    Room addExistingRoom(int id, int number, BedType[] bedTypes) {
        Room room = new Room(id, number, bedTypes);
        rooms.add(room);
        return room;
    }

    List<Room> getAllRooms() {
        return this.rooms;
    }

    void saveAll() {
        String name = "rooms.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);
        StringBuilder sb = new StringBuilder("");
        for (Room room : rooms) {
            sb.append(room.toCSV());
        }
        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "save", "room data");
        }
    }

    public void readAll() {
        String name = "rooms.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        if(!Files.exists(file)){
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] roomsAsString = data.split(System.getProperty("line.separator"));

            for (String roomAsString : roomsAsString) {
                String[] roomData = roomAsString.split(",");
                if (roomData[0] == null || roomData[0].trim().isEmpty()) {
                    continue;
                }
                int id = Integer.parseInt(roomData[0]);
                int number = Integer.parseInt(roomData[1]);
                String bedTypesData = roomData[2];
                String[] bedTypesAsString = bedTypesData.split("#");
                BedType[] bedTypes = new BedType[bedTypesAsString.length];
                for (int i = 0; i < bedTypes.length; i++) {
                    bedTypes[i] = BedType.valueOf(bedTypesAsString[i]);
                }
                addExistingRoom(id, number, bedTypes);
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "guest data");
        }
    }

    private int findNewId() {
        int max = 0;
        for (Room room : rooms) {
            if (room.getId() > max) {
                max = room.getId();
            }
        }
        return max + 1;
    }

    public void remove(int id) {
        int roomToBeRemovedIndex = -1;

        for (int i = 0; i < this.rooms.size(); i++) {
            if (this.rooms.get(i).getId() == id) {
                roomToBeRemovedIndex = i;
                break;
            }
        }
        if (roomToBeRemovedIndex > -1) {
            this.rooms.remove(roomToBeRemovedIndex);
        } else {
            System.out.println("Nie ma pokoju o podanym indeksie");
        }
    }

    public void edit(int id, int number, BedType[] bedTypes) {
        remove(id);
        addExistingRoom(id,number,bedTypes);
    }

    public Room getById(int id) {
        for(Room room:this.rooms){
            if(room.getId()==id){
                return room;
            }
        }
        return null;
    }
}


