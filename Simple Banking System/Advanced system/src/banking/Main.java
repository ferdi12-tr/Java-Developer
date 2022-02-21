package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String url = "jdbc:sqlite:" + args[1];
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (id INTEGER, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)");

                boolean isExit = false; // check if user press exit or log-out
                while (!isExit) {
                    System.out.println("1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit");

                    int key = scanner.nextInt();
                    switch (key) {
                        case 1:
                            System.out.println("Your card has been created");
                            Account account = new Account();
                            addToDataBase(account, con);  // create an account and write to the database
                            // print the information about created card
                            System.out.printf("Your card number:\n%s\n", account.getCardNumber());
                            System.out.printf("Your card PIN:\n%s\n", account.getPin());
                            break;
                        case 2:
                            System.out.println("Enter your card number:");
                            String inputCardNumber = scanner.next();
                            System.out.println("Enter your PIN:");
                            String inputPin = scanner.next();
                            // check the correctness of account and print the menu,
                            boolean isLogOut = checkAccount(inputCardNumber, inputPin, con);
                            if (!isLogOut) { // if returned value is false, this means that the user pressed exit otherwise, value is true, this means that user pressed log-out
                                con.close(); // close the connection
                                isExit = true; // exit the programs
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

    private static void addToDataBase(Account account, Connection con) {
        String insert = "INSERT INTO card VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(insert)) {
            statement.setInt(1, Account.getNumberOfAccount());
            statement.setString(2, account.getCardNumber());
            statement.setString(3, account.getPin());
            statement.setInt(4, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}
    }

    private static boolean checkAccount(String inputCardNumber, String inputPin, Connection con) {
        String selectAccount = "SELECT * FROM card WHERE number = ? AND pin = ?";
        try (PreparedStatement statement = con.prepareStatement(selectAccount)) {
            statement.setString(1, inputCardNumber);
            statement.setString(2, inputPin);

            ResultSet result = statement.executeQuery(); // this returns the corresponding rows

            if (result.next()) { // move the cursor on first row
                System.out.println("You have successfully logged in!");
                return logInMenu(result, con, inputCardNumber); // if the entered numbers is true then print the information
            } else {
                System.out.println("Wrong card number or PIN!");
                return true; // make sure we still are in the program and program is running
            }
        } catch (SQLException e) {e.printStackTrace();}
        return true; // make sure we still are in the program and program is running
    }

    private static boolean logInMenu(ResultSet result, Connection con, String inputCardNumber) throws SQLException {
        con.setAutoCommit(false); // commit the changes manually
        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            int key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.printf("Balance: %d\n", result.getInt("balance")); // print the balance of login user
                    break;
                case 2:
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    int newBalance = result.getInt("balance") + income;
                    // updating the balance
                    PreparedStatement statement = con.prepareStatement("UPDATE card SET balance = ? WHERE number = ?");
                    statement.setInt(1, newBalance);
                    statement.setString(2, inputCardNumber);
                    statement.executeUpdate();
                    con.commit(); // do not forget committing

                    // after updating the balance, we should update result because the result must include the updated balance
                    statement = con.prepareStatement("SELECT * FROM card WHERE number = ?");
                    statement.setString(1, inputCardNumber);
                    result = statement.executeQuery(); // updated result
                    result.next(); // move the cursor on the current user row
                    System.out.println("Income was added!");
                    break;
                case 3:
                    System.out.println("Enter card number:");
                    String toTransfer = scanner.next(); // the card number that we want to transfer to

                    // when the user entered the card number, we check the validation of card. in order to check the validation, we use the check bit, that is lunch algorithm
                    if (!Objects.equals(String.valueOf(toTransfer.charAt(15)), String.valueOf(Account.generateCheckSum(toTransfer)))) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                        break;
                    } else if (Objects.equals(toTransfer, inputCardNumber)) {
                        System.out.println("You can't transfer money to the same account!");
                        break;
                    } else {
                        statement = con.prepareStatement("SELECT * FROM card WHERE number = ?");
                        statement.setString(1, toTransfer);
                        ResultSet result2 = statement.executeQuery();

                        if (!result2.next()) {
                            System.out.println("Such a card does not exist."); // if the database doesn't contain the card number to which money will be transferred
                            break;
                        } else {
                            System.out.println("Enter how much money you want to transfer:");
                            int money = scanner.nextInt(); // this money withdraws from current user account

                            if (result.getInt("balance") < money) {
                                System.out.println("Not enough money!");
                                break;
                            } else {
                                // save the users' money to check if the transaction is completed successfully or not
                                int moneyOfUser1 = result.getInt("balance"); // this user send money
                                int moneyOfUser2 = result2.getInt("balance"); // this user receive money

                                Savepoint savepoint1 = con.setSavepoint(); // set a savepoint before withdrawing money

                                statement = con.prepareStatement("UPDATE card SET balance = ? WHERE number = ?");

                                // update money of user1
                                statement.setInt(1,result.getInt("balance") - money);
                                statement.setString(2, inputCardNumber);
                                statement.executeUpdate();

                                // update money of user2
                                statement.setInt(1, money);
                                statement.setString(2, toTransfer);
                                statement.executeUpdate();

                                // do a query to check the transaction and update the result objects
                                statement = con.prepareStatement("SELECT * FROM card WHERE number = ?");
                                statement.setString(1, inputCardNumber);
                                result = statement.executeQuery();
                                statement.setString(1, toTransfer);
                                result2 = statement.executeQuery();

                                // if the deposited money is not equals the withdrawal money, roll back to savepoint and discard all transaction. Otherwise, commit the changes
                                if (moneyOfUser1 - result.getInt("balance") == result2.getInt("balance") - moneyOfUser2) {
                                    System.out.println("Success!");
                                    con.commit();
                                    break;
                                } else {
                                    con.rollback(savepoint1);
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    statement = con.prepareStatement("DELETE FROM card WHERE number = ?");
                    statement.setString(1, inputCardNumber); // delete the current users' row
                    statement.executeUpdate();
                    con.commit();
                    System.out.println("The account has been closed!");
                    return true; // say that we do not press exit
                case 5: // user press log out, not exit
                    System.out.println("You have successfully logged out!");
                    return true; // we say that user press log out, and we return the logout information
                case 0: // the user press exit
                    return false;
            }
        }
    }
}