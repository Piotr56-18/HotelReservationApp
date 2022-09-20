package pl.application.domain.room;

import pl.application.domain.room.dto.RoomDTO;
import pl.application.exceptions.WrongOptionException;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private static final RoomRepository repository = new RoomRepository();
    public Room createNewRoom(int number, int[] bedTypesOptions) {
        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i = 0; i < bedTypesOptions.length; i++) {

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return repository.createNewRoom(number, bedTypes);
    }

    public List<Room> getAllRooms() {
        return this.repository.getAllRooms();
    }
    public void saveAll(){
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void removeRoom(int id) {
        this.repository.remove(id);
    }

    public void editRoom(int id, int number, int[] bedTypesOptions) {
        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i = 0; i < bedTypesOptions.length; i++) {

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        this.repository.edit(id,number,bedTypes);
    }

    public Room getRoomById(int roomId) {
        return this.repository.getById(roomId);
    }
    public List<RoomDTO>getAllAsDTO(){
        List<RoomDTO>result = new ArrayList<>();
        List<Room>allRooms = repository.getAllRooms();
        for(Room room:allRooms){
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }
        return result;
    }
}

