package banking;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        ArrayList<Account> accounts = new ArrayList<>();
        boolean isExit = false;

        while (!isExit) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");

            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.println("Your card has been created");
                    accounts.add(new Account()); // create new account and add list
                    printClientInfo(accounts.get(accounts.size() - 1)); // print the information of last added account
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String inputCardNumber = scanner.next();
                    System.out.println("Enter your PIN:");
                    String inputPin = scanner.next();
                    checkAccount(inputCardNumber, inputPin, accounts);
                    break;
                case 0:
                    System.out.println("Bye!");
                    isExit = true;
                    break;
            }
        }
    }

    private static void checkAccount(String inputCardNumber, String inputPin, ArrayList<Account> accounts) {
        for (Account account : accounts) {
            if (Objects.equals(account.getCardNumber(), inputCardNumber)
                    && Objects.equals(account.getPin(), inputPin)) {
                System.out.println("You have successfully logged in!");
                printLogInMenu(account); // if the entered numbers is true then print the information
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
    }

    private static void printLogInMenu(Account account) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.printf("Balance: %d\n", account.getBalance());
                    break;
                case 2:
                case 0:
                    System.out.println("You have successfully logged out!");
                    isExit = true;

            }
        }
    }

    private static void printClientInfo(Account account) {
        System.out.printf("Your card number:\n%s\n", account.getCardNumber());
        System.out.printf("Your card PIN:\n%s\n", account.getPin());
    }
}