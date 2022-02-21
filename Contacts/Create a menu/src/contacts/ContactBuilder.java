package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactBuilder implements Builder {

    private String name;
    private String surname;
    private String number;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setNumber(String number) {
        if (Action.checkValidityPhoneNumber(number)) {
            this.number = number;
            System.out.print("The record added.\n");
        } else {
            System.out.print("Wrong number format!\n");
            System.out.print("The record added.\n");
            this.number = "[no number]";
        }
    }

    public Contact getContact() {
        return new Contact(name, surname, number);
    }

}
