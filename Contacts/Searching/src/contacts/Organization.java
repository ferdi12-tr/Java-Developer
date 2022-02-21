package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Organization extends Contact {
    private static final long serialVersionUID = 1L;
    transient private final static Scanner scanner = new Scanner(System.in);

    private String address;

    public Organization(String name, String address, String number) {
        super(number, name);
        this.address = address;
    }

    @Override
    public String toString() {
        return super.getName();
    }

    @Override
    public String printInfo() {

        String lastEdittedTime = super.getLastEdittedTime().format(DateTimeFormatter.ISO_DATE_TIME);
        String createdTime = super.getCreatedTime().format(DateTimeFormatter.ISO_DATE_TIME);

        return String.format(
                "Organization name: %s\n" +
                "Address: %s\n" +
                "Number: %s\n" +
                "Time created: %s\n" +
                "Time last edit: %s\n", super.getName(), address, super.getNumber(),
                createdTime, lastEdittedTime
        );
    }

    public void setOrganizationName(String name) {
        super.setName(name);
        super.setLastEdittedTime(LocalDateTime.now());
    }

    public void setAddress(String address) {
        this.address = address;
        super.setLastEdittedTime(LocalDateTime.now());
    }

    @Override
    public Action record() {
        System.out.print("[record] Enter action (edit, delete, menu): ");
        String action = scanner.nextLine();

        if (action.equals(Action.EDIT.getAction())) {
            System.out.print("Select a field (name, address, number): ");
            String field = scanner.nextLine();
            if (field.equals(Action.NAME.getAction())) {
                System.out.print("Enter name: ");
                super.setName(scanner.nextLine());
                System.out.println("Saved");
                System.out.print(this.printInfo());
            } else if (field.equals(Action.ADDRESS.getAction())) {
                System.out.print("Enter address: ");
                setAddress(scanner.nextLine());
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
