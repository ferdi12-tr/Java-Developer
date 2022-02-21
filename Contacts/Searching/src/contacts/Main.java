package contacts;

import java.io.IOException;
import java.util.ArrayList;
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

            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String action = scanner.nextLine();

            if (action.equals(Action.COUNT.getAction())) {
                runCount();
            } else if (action.equals(Action.SEARCH.getAction())) {
                runSearch();
            } else if (action.equals(Action.EXIT.getAction())) {
                break;
            } else if (action.equals(Action.ADD.getAction())) {
                runAdd();
            } else if (action.equals(Action.LIST.getAction())) {
                runList();
            }
        }
    }

    private static void runList() {
        System.out.println(phoneBook);
        System.out.print("[list] Enter action ([number], back): ");
        String index = scanner.nextLine();

        if (index.matches(Action.BACK.getAction())) {
            return;
        }
        System.out.println(phoneBook.getContacts().get(Integer.parseInt(index) - 1).printInfo());

        while (true) {
            Action action = phoneBook.getContacts().get(Integer.parseInt(index) - 1).record();
            if (action == Action.MENU) {
                break;
            } else if (action == Action.DELETE) {
                phoneBook.getContacts().remove(Integer.parseInt(index) - 1);
            } else {
                continue;
            }
        }

    }

    private static void runSearch() {
        boolean turnMenu = false;
        while (!turnMenu) {
            System.out.print("Enter search query: ");
            String query = "(?i).*" + scanner.nextLine() + ".*";
            ArrayList<Contact> arrlist = new ArrayList<>();
            int count = 0;
            for (Contact contact : phoneBook.getContacts()) {
                if (contact.toString().matches(query) || contact.getNumber().matches(query)) {
                    arrlist.add(contact);
                    count++;
                }
            }
            System.out.printf("Found %d results:\n", count);
            for (int i = 1; i <= count; i++) {
                System.out.printf("%d. %s\n", i, arrlist.get(i - 1).toString());
            }

            System.out.print("[search] Enter action ([number], back, again): ");
            String action = scanner.nextLine();

            if (action.equals(Action.AGAIN.getAction())) {
                turnMenu = false;
            } else if (action.equals(Action.BACK.getAction())) {
                turnMenu = true;
            } else { // search the contact by entering the number listed

                while (true) {
                    Action action1 = arrlist.get(Integer.parseInt(action) - 1).record();
                    if (action1 == Action.MENU) {
                        turnMenu = true;
                        break;
                    } else if (action1 == Action.DELETE){
                        phoneBook.getContacts().remove(arrlist.get(Integer.parseInt(action) - 1));
                        turnMenu = true;
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private static void runCount() {
        System.out.printf("The Phone Book has %d records.\n", phoneBook.lengthOfPhoneBook());
        System.out.println();
    }

    private static void runAdd() {
        Contact tempContact;
        String fileName = "phonebook.txt";

        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        if (type.equals(Action.PERSON.getAction())) {
            builder = new PersonBuilder();
            director.buildPerson(builder);
            tempContact = builder.getPerson();
            phoneBook.addPhoneBook(tempContact);

            try {
                SerializationUtils.serialize(tempContact, fileName);
            } catch (IOException e) {e.printStackTrace();}

        } else if (type.equals(Action.ORGANIZATION.getAction())) {
            builder = new OrganizationBuilder();
            director.buildOrganization(builder);
            tempContact = builder.getOrganization();
            phoneBook.addPhoneBook(tempContact);

            try {
                SerializationUtils.serialize(tempContact, fileName);
            } catch (IOException e) {e.printStackTrace();}
        }
        System.out.println();
    }
}

