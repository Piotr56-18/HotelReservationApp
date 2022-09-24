package pl.application.domain.guest;

import pl.application.exceptions.PersistenceToFileException;
import pl.application.util.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private final List<Guest> guests = new ArrayList<>();
    private final static GuestRepository instance = new GuestRepository();

    private GuestRepository(){};

    public static GuestRepository getInstance(){
        return instance;
    }

    Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(findNewId(), firstName, lastName, age, gender);
        guests.add(guest);
        return guest;
    }

    Guest addExistingGuest(int id, String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(id, firstName, lastName, age, gender);
        guests.add(guest);
        return guest;
    }

    List<Guest> getAll() {
        return this.guests;
    }

    void saveAll() {
        String name = "guests.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);
        StringBuilder sb = new StringBuilder("");
        for (Guest guest : this.guests) {
            sb.append(guest.toCSV());
        }
        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "save", "guest data");
        }
    }

    void readAll() {
        String name = "guests.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] guestsAsString = data.split(System.getProperty("line.separator"));

            for (String guestAsString : guestsAsString) {
                String[] guestData = guestAsString.split(",");
                if (guestData[0] == null || guestData[0].trim().isEmpty()) {
                    continue;
                }
                int id = Integer.parseInt(guestData[0]);
                int age = Integer.parseInt(guestData[3]);
                Gender gender = Gender.valueOf(guestData[4]);
                addExistingGuest(id, guestData[1], guestData[2], age, gender);
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "guest data");
        }
    }

    private int findNewId() {
        int max = 0;
        for (Guest guest : guests) {
            if (guest.getId() > max) {
                max = guest.getId();
            }
        }
        return max + 1;
    }

    public void remove(int id) {
        int guestToBeRemovedIndex = -1;

        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).getId() == id) {
                guestToBeRemovedIndex = i;
                break;
            }
        }
            if (guestToBeRemovedIndex > -1) {
                this.guests.remove(guestToBeRemovedIndex);
            } else {
                System.out.println("Nie ma gościa o podanym indeksie");
            }
        }

    public void edit(int id, String firstName, String lastName, int age, Gender gender) {
        this.remove(id);
        this.addExistingGuest(id,firstName,lastName,age,gender);
    }

    public Guest findById(int id) {
        for(Guest guest :this.guests){
            if(guest.getId()==id){
                return guest;
            }
        }
        return null;
    }
}
