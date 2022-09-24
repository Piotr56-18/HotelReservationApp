package pl.application.domain.guest;

import pl.application.domain.guest.dto.GuestDTO;

public class Guest {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Gender gender;

    Guest(int id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }
    public GuestDTO generateDTO() {
        String gender = "Mężczyzna";
        if(this.gender==Gender.FEMALE){
            gender = "Kobieta";
        }
        return new GuestDTO(this.id, this.firstName,this.lastName,this.age,gender);
    }

    public String getInfo() {
        return String.format("%d %s %s (%d) (%s)", this.id, this.firstName, this.lastName, this.age, this.gender);
    }
    String toCSV(){
        return String.format("%s, %s,%s,%d,%s%s",this.id, this.firstName,this.lastName,this.age,this.gender,System.getProperty("line.separator"));
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
