package banking;

import java.util.Random;

public class Account {

    private final String BIN = "400000"; // in our case Major Industry Identifier (MII) must be 400000
    private final String cardNumber; // card number must be unique
    private final String pin; // and of course pin too
    private int balance = 0; // initialize balance with 0
    Random random = new Random();

    public Account() { // when instantiated, card number and pin must be created
        cardNumber = createCardNumber();
        pin = createPin();
    }

    private String createPin() { // create 4 digit pin number
        return String.format("%04d", random.nextInt(10_000));
    }

    private String createCardNumber() { // create 9 digit account Identifier number, check bit
        // concatenate all to create full card number
        String accountIdentifierNumber = String.format("%09d", random.nextInt(1_000_000_000));
        String checkSum = String.format("%d", random.nextInt(10));
        return BIN + accountIdentifierNumber + checkSum;
    }
    // getters
    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }
}
