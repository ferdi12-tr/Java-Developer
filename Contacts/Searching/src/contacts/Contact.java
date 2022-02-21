package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String name;
    private  String surname;
    private String number = "";
    transient private LocalDateTime lastEdittedTime;
    transient private final LocalDateTime createdTime;

    abstract public String printInfo(); // each contacts must implement  this method according to their fields

    abstract public Action record(); // each contacts must implement  this method according to their fields

    public Contact(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.createdTime = LocalDateTime.now();
        this.lastEdittedTime = LocalDateTime.now();
    }

    public Contact(String number, String name) {
        this.number = number;
        this.name = name;
        this.createdTime = LocalDateTime.now();
        this.lastEdittedTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return name + " " + surname + ", "+ number;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastEdittedTime() {
        return lastEdittedTime;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (Action.checkValidityPhoneNumber(number)) {
            this.number = number;
        } else {
            System.out.print("Wrong number format!\n");
            this.number = "[no number]";
        }

        lastEdittedTime = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
        lastEdittedTime = LocalDateTime.now();
    }

    public void setSurname(String surname) {
        this.surname = surname;
        lastEdittedTime = LocalDateTime.now();
    }

    public void setLastEdittedTime(LocalDateTime lastEdittedTime) {
        this.lastEdittedTime = lastEdittedTime;
    }

    @Deprecated // we implement this method but stage 4 make this process more suitable, and we do not use it anymore
    public static final boolean isPerson(Contact contact) {
        return contact.getClass() == Person.class;
    }
}
