package contacts;

public class Contact {

    private  String name;
    private  String surname;
    private String number = "";

    public Contact(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " " + surname + ", "+ number;
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
            System.out.print("The record added.\n");
        } else {
            System.out.print("Wrong number format!\n");
            System.out.print("The record added.\n");
            this.number = "[no number]";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
