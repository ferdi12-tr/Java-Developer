package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Person extends Contact {
    private String birthDay;
    private String gender;


    public Person(String name, String surname, String number, String  birthDay, String gender) {
        super(name, surname, number);
        this.birthDay = birthDay;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return super.getName() + " " + super.getSurname();
    }

    @Override
    public String printInfo() {
        if (!birthDay.equals("[no data]")) {
            LocalDateTime dateTime = LocalDateTime.parse(birthDay);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            birthDay = dateTime.format(formatter);
        }

        String lastEdittedTime = super.getLastEdittedTime().format(DateTimeFormatter.ISO_DATE_TIME);
        String createdTime = super.getCreatedTime().format(DateTimeFormatter.ISO_DATE_TIME);

        return String.format("Name: %s\n" +
                "Surname: %s\n" +
                "Birth date: %s\n" +
                "Gender: %s\n" +
                "Number: %s\n" +
                "Time created: %s\n" +
                "Time last edit: %s\n", super.getName(), super.getSurname(),
                birthDay, gender, super.getNumber(), createdTime, lastEdittedTime);
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        super.setLastEdittedTime(LocalDateTime.now());
    }

    public void setGender(String gender) {
        this.gender = gender;
        super.setLastEdittedTime(LocalDateTime.now());
    }
}
