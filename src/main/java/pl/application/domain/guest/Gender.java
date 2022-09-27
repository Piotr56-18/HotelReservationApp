package pl.application.domain.guest;

import pl.application.util.Properties;

public enum Gender {
    MALE(Properties.MALE),
    FEMALE(Properties.FEMALE);

    private final String asStr;

    Gender(String asStr){
        this.asStr=asStr;
    }
    @Override
    public String toString(){
        return this.asStr;
    }
}
