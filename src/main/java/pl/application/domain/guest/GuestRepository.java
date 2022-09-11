package pl.application.domain.guest;

public class GuestRepository {

    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest guest = new Guest(firstName, lastName, age, gender);
        return guest;
    }
}
