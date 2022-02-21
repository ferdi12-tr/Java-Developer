package contacts;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static Scanner scanner;
    private static PhoneBook phoneBook;
    private static Director director;
    private static Builder builder;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        phoneBook = new PhoneBook();
        director = new Director();

        while (true) {

            System.out.print("Enter action (add, remove, edit, count, info, exit): ");
            String action = scanner.nextLine();

            if (action.equals(Action.ADD.getAction())) {
                runAdd();
            } else if (action.equals(Action.REMOVE.getAction())) {
                runRemove();
            } else if (action.equals(Action.EDIT.getAction())) {
                runEdit();
            } else if (action.equals(Action.COUNT.getAction())) {
                runCount();
            } else if (action.equals(Action.INFO.getAction())) {
                runInfo();
            } else if (action.equals(Action.EXIT.getAction())) {
                break;
            }
        }

    }

    private static void runCount() {
        System.out.printf("The Phone Book has %d records.\n", phoneBook.lengthOfPhoneBook());
        System.out.println();
    }

    private static void runRemove() {
        if (phoneBook.lengthOfPhoneBook() == 0) {
            System.out.println("No records to remove!");
        } else {
            System.out.print(phoneBook);
            System.out.print("Select a record: ");
            String select = scanner.nextLine();
            phoneBook.getContacts().remove(Integer.parseInt(select) - 1);
            System.out.print("The record removed!\n");
            System.out.println();
        }
    }

    private static void runEdit() {
        if (phoneBook.lengthOfPhoneBook() == 0) {
            System.out.print("No records to edit!");
            return;
        }
        System.out.print(phoneBook);
        System.out.print("Select a record: ");
        String record = scanner.nextLine();
        if (Contact.isPerson(phoneBook.getContacts().get(Integer.parseInt(record) - 1))) {
            Person contact = (Person) phoneBook.getContacts().get(Integer.parseInt(record) - 1);
            editPerson(contact);
            System.out.print("The record updated!\n");
        } else {
            Organization contact = (Organization) phoneBook.getContacts().get(Integer.parseInt(record) - 1);
            editOrganization(contact);
            System.out.print("The record updated!\n");
        }
        System.out.println();
    }

    private static void editOrganization(Organization contact) {
        System.out.print("Select a field (name, address, number): ");
        String field = scanner.nextLine();
        if (field.equals(Action.NAME.getAction())) {
            System.out.print("Enter organization name: ");
            contact.setOrganizationName(scanner.nextLine());
        } else if (field.equals(Action.ADDRESS.getAction())) {
            System.out.print("Enter address: ");
            contact.setAddress(scanner.nextLine());
        } else if (field.equals(Action.NUMBER.getAction())) {
            System.out.print("Enter number: ");
            contact.setNumber(scanner.nextLine());
        }
    }

    private static void editPerson(Person contact) {
        System.out.print("Select a field (name, surname, birth, gender, number): ");
        String field = scanner.nextLine();
        if (field.equals(Action.NUMBER.getAction())) {
            System.out.print("Enter number: ");
            contact.setNumber(scanner.nextLine());
        } else if (field.equals(Action.NAME.getAction())) {
            System.out.print("Enter name: ");
            contact.setName(scanner.nextLine());
        } else if (field.equals(Action.SURNAME.getAction())) {
            System.out.print("Enter surname: ");
            contact.setSurname(scanner.nextLine());
        } else if (field.equals(Action.BIRTH.getAction())) {
            System.out.print("Enter birth date: ");
            contact.setBirthDay(scanner.nextLine());
        } else if (field.equals(Action.GENDER.getAction())) {
            System.out.print("Enter birth date: ");
            contact.setGender(scanner.nextLine());
        }
    }

    private static void runInfo() {
        System.out.print(phoneBook);
        System.out.print("Enter index to show info: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print(phoneBook.getContacts().get(index - 1).printInfo());
        System.out.println();
    }

    private static void runAdd() {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        if (type.equals(Action.PERSON.getAction())) {
            builder = new PersonBuilder();
            director.buildPerson(builder);
            phoneBook.addPhoneBook(builder.getPerson());
            System.out.print("The record added.\n");
        } else if (type.equals(Action.ORGANIZATION.getAction())) {
            builder = new OrganizationBuilder();
            director.buildOrganization(builder);
            phoneBook.addPhoneBook(builder.getOrganization());
            System.out.print("The record added.\n");
        }
        System.out.println();
    }
}

