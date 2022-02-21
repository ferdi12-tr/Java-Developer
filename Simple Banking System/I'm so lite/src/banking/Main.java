package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {

        String url = "jdbc:sqlite:" + args[1];
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (id INTEGER, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)");

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
                            addToDataBase(accounts.get(accounts.size() - 1), statement);
                            printClientInfo(accounts.get(accounts.size() - 1)); // print the information of last added account
                            break;
                        case 2:
                            System.out.println("Enter your card number:");
                            String inputCardNumber = scanner.next();
                            System.out.println("Enter your PIN:");
                            String inputPin = scanner.next();
                            boolean isLogOut = checkAccount(inputCardNumber, inputPin, accounts);
                            if (!isLogOut) {
                                con.close();
                                isExit = true;
                            }
                            break;

                        case 0:
                            System.out.println("Bye!");
                            isExit = true;
                            break;
                    }
                }
            } catch (SQLException e) {e.printStackTrace();}
        } catch (SQLException e) {e.printStackTrace();}
    }

    private static void addToDataBase(Account account, Statement statement) throws SQLException {
        int i = statement.executeUpdate(String.format("INSERT INTO card VALUES (%d, %s, %s, %d)",
                Account.getNumberOfAccount(), account.getCardNumber(), account.getPin(), account.getBalance()));
    }

    /*
        private static void addToDataBase(String url) {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);

            try (Connection con = dataSource.getConnection()) {
                try (Statement statement = con.createStatement()) {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (id INTEGER, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)");
                    for (Account account : accounts) {
                        int i = statement.executeUpdate(String.format("INSERT INTO card VALUES (%d, %s, %s, %d)",
                                Account.getNumberOfAccount(), account.getCardNumber(), account.getPin(), account.getBalance()));
                    }
                } catch (SQLException e) {e.printStackTrace();}
            } catch (SQLException e) {e.printStackTrace();}
        }
    */
    private static boolean checkAccount(String inputCardNumber, String inputPin, ArrayList<Account> accounts) {
        for (Account account : accounts) {
            if (Objects.equals(account.getCardNumber(), inputCardNumber)
                    && Objects.equals(account.getPin(), inputPin)) {
                System.out.println("You have successfully logged in!");
                return printLogInMenu(account); // if the entered numbers is true then print the information
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        }
        return true; // return true if else statement is true
    }

    private static boolean printLogInMenu(Account account) {
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.printf("Balance: %d\n", account.getBalance());
                    break;
                case 2: // the use press log out, not exit
                    System.out.println("You have successfully logged out!");
                    return true; // we say that user press log out, and we return the logout information
                case 0: // the user press exit
                    return false;
            }
        }
    }

    private static void printClientInfo(Account account) {
        System.out.printf("Your card number:\n%s\n", account.getCardNumber());
        System.out.printf("Your card PIN:\n%s\n", account.getPin());
    }
}