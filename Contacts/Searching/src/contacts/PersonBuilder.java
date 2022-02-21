package contacts;

import java.time.LocalDateTime;

public class PersonBuilder implements Builder {

    private String name;
    private String surname;
    private String number;
    private String birthDay;
    private String gender;

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

    @Deprecated
    public void setOrganizationName(String organizationName) {}

    @Deprecated
    public void setAddress(String address) {}

    @Deprecated
    public Contact getOrganization() {return null;}

    @Override
    public void setBirthDay(String birthDay) {

        this.birthDay = birthDay;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public Contact getPerson() {
        return new Person(name, surname, number, birthDay, gender);
    }
}
