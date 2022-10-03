package pl.application.domain.room;

import org.junit.jupiter.api.Test;
import pl.application.domain.room.BedType;
import pl.application.domain.room.Room;
import pl.application.domain.room.dto.RoomDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    public void toCSVTest(){
        List<BedType> beds = new ArrayList<>();
        beds.add(BedType.SINGLE);
        beds.add(BedType.DOUBLE);
        beds.add(BedType.KING_SIZE);
        Room room = new Room(1,306,beds);
        String csvTemplate = "1,306,Pojedyncze#Podwójne#Królewskie"+System.getProperty("line.separator");
        String createdCSV = room.toCSV();
        assertEquals(csvTemplate,createdCSV,"Porównanie wygenerowaniych CSV");
    }
    @Test
    public void toCSVWithNullBedListTest(){
        //given
        Room room = new Room(1,306,null);
        //when
        String createdCSV = room.toCSV();
        //then
        assertNotNull(room.getBeds());
        String csvTemplate = "1,306,"+System.getProperty("line.separator");
        assertEquals(csvTemplate,createdCSV,"Porównanie wygenerowaniych formatów przy liście łóżek == null");
    }
    @Test
    public void toDTOTest(){
        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1,306,beds);
        //when
        RoomDTO roomDTO = room.generateDTO();
        //then
        assertEquals(roomDTO.getId(),1);
        assertEquals(roomDTO.getNumber(),306);
        assertEquals(roomDTO.getBedsCount(),3);
        assertEquals(roomDTO.getRoomSize(),5);
        assertEquals(roomDTO.getBeds(), "Pojedyncze,Podwójne,Królewskie");
    }
    @Test
    public void toDTOFromRoomWithNullBedsListTest(){
        //given
        Room room = new Room(1,306,null);
        //when
        RoomDTO roomDTO = room.generateDTO();
        //then
        assertEquals(roomDTO.getId(),1);
        assertEquals(roomDTO.getNumber(),306);
        assertEquals(roomDTO.getBedsCount(),0);
        assertEquals(roomDTO.getRoomSize(),0);
        assertEquals(roomDTO.getBeds(), "");
    }
}
