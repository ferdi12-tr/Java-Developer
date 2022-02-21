package contacts;

import java.util.Scanner;

public class Director {

    private Builder builder;

    Scanner scanner = new Scanner(System.in);

    public void buildContact(Builder builder) {
        System.out.print("Enter the name: ");
        builder.setName(scanner.nextLine());
        System.out.print("Enter the surname: ");
        builder.setSurname(scanner.nextLine());
        System.out.print("Enter the number: ");
        builder.setNumber(scanner.nextLine());
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void buildPerson(Builder builder) {
        System.out.print("Enter the name: ");
        builder.setName(scanner.nextLine());

        System.out.print("Enter the surname: ");
        builder.setSurname(scanner.nextLine());

        System.out.print("Enter the birth date: ");
        String date = scanner.nextLine();
        if (date.equals("")) {
            System.out.print("Bad birth date!\n");
            builder.setBirthDay("[no data]");
        } else {
            builder.setBirthDay(date);
        }

        System.out.print("Enter the gender (M, F): ");
        String gender = scanner.nextLine();
        if (gender.equals("")) {
            System.out.print("Bad gender!\n");
            builder.setGender("[no data]");
        } else {
            builder.setGender(gender);
        }

        System.out.print("Enter the number: ");
        builder.setNumber(scanner.nextLine());
    }

    public void buildOrganization(Builder builder) {
        System.out.print("Enter the organization name: ");
        builder.setOrganizationName(scanner.nextLine());
        System.out.print("Enter the address: ");
        builder.setAddress(scanner.nextLine());
        System.out.print("Enter the number: ");
        builder.setNumber(scanner.nextLine());
    }
}
