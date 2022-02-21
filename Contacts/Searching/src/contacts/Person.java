package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Person extends Contact {
    private static final long serialVersionUID = 1L;
    transient private final static Scanner scanner = new Scanner(System.in);

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

    @Override
    public Action record() {
        System.out.print("[record] Enter action (edit, delete, menu): ");
        String action = scanner.nextLine();

        if (action.equals(Action.EDIT.getAction())) {
            System.out.print("Select a field (name, surname, birth, gender, number): ");
            String field = scanner.nextLine();
            if (field.equals(Action.NAME.getAction())) {
                System.out.print("Enter name: ");
                super.setName(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            } else if (field.equals(Action.SURNAME.getAction())) {
                System.out.print("Enter surname: ");
                super.setSurname(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            } else if (field.equals(Action.BIRTH.getAction())) {
                System.out.print("Enter birth date: ");
                setBirthDay(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            } else if (field.equals(Action.GENDER.getAction())) {
                setGender(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            } else if (field.equals(Action.NUMBER.getAction())) {
                setNumber(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            }
            return Action.EDIT;
        } else if (action.equals(Action.MENU.getAction())) {
            return Action.MENU;
        } else {
            return Action.DELETE;
        }
    }
}
