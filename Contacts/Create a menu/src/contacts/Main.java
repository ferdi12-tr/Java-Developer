package contacts;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static PhoneBook phoneBook;
    public static void main(String[] args) {

        ContactDirector director = new ContactDirector();
        ContactBuilder builder;

        Scanner scanner = new Scanner(System.in);
        phoneBook = new PhoneBook();


        while (true) {
            System.out.print("Enter action (add, remove, edit, count, list, exit): ");
            String action = scanner.nextLine();

            if (action.equals(Action.ADD.getAction())) {
                builder = new ContactBuilder();
                director.buildContact(builder);
                phoneBook.addPhoneBook(builder.getContact()); // pass the created contact to phone book

            } else if (action.equals(Action.REMOVE.getAction())) {
                if (phoneBook.lengthOfPhoneBook() == 0) {
                    System.out.println("No records to remove!");
                } else {
                    System.out.print(phoneBook);
                    System.out.print("Select a record: ");
                    String select = scanner.nextLine();
                    phoneBook.getContacts().remove(Integer.parseInt(select) - 1);
                    System.out.print("The record removed!\n");
                }

            } else if (action.equals(Action.EDIT.getAction())) {
                if (phoneBook.lengthOfPhoneBook() == 0) {
                    System.out.println("No records to edit!");
                } else {
                    System.out.print(phoneBook);
                    System.out.print("Select a record: ");
                    String record = scanner.nextLine();
                    System.out.print("Select a field (name, surname, number): ");
                    action = scanner.nextLine();
                    if (Objects.equals("number", action)) {
                        System.out.print("Enter number: ");
                        String number = scanner.nextLine();
                        phoneBook.getContacts().get(Integer.parseInt(record) - 1).setNumber(number);
                        System.out.println("The record updated!");
                    } else if (Objects.equals("name", action)) {
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        phoneBook.getContacts().get(Integer.parseInt(record) - 1).setName(name);
                    } else if (Objects.equals("surname", action)) {
                        System.out.print("Enter surname: ");
                        String surname = scanner.next();
                        phoneBook.getContacts().get(Integer.parseInt(record) - 1).setSurname(surname);
                    }
                }

            } else if (action.equals(Action.COUNT.getAction())) {
                System.out.printf("The Phone Book has %d records.\n", phoneBook.lengthOfPhoneBook());
            } else if (action.equals(Action.LIST.getAction())) {
                System.out.print(phoneBook);
            } else if (action.equals(Action.EXIT.getAction())) {
                break;
            }
        }
    }
}
