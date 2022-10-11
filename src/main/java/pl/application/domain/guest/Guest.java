package pl.application.domain.guest;

import pl.application.domain.guest.dto.GuestDTO;

import javax.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private int age;

    @Enumerated
    private Gender gender;

    public Guest(){}

    public Guest(long id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Guest(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public GuestDTO generateDTO() {
        String gender = "Mężczyzna";
        if (this.gender == Gender.FEMALE) {
            gender = "Kobieta";
        }
        return new GuestDTO(this.id, this.firstName, this.lastName, this.age, gender);
    }

    public String getInfo() {
        return String.format("%d %s %s (%d) (%s)", this.id, this.firstName, this.lastName, this.age, this.gender);
    }

    String toCSV() {
        return String.format("%s, %s,%s,%d,%s%s", this.id, this.firstName, this.lastName, this.age, this.gender, System.getProperty("line.separator"));
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
