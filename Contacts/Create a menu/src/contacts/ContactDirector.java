package contacts;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ContactDirector {
    Scanner scanner = new Scanner(System.in);

    public void buildContact(Builder builder) {
        System.out.print("Enter the name: ");
        builder.setName(scanner.nextLine());
        System.out.print("Enter the surname: ");
        builder.setSurname(scanner.nextLine());
        System.out.print("Enter the number: ");
        builder.setNumber(scanner.nextLine());
    }
}
