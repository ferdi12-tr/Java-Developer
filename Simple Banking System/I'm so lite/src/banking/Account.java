package banking;

import java.util.Random;

public class Account {

    private final String BIN = "400000"; // in our case Major Industry Identifier (MII) must be 400000
    private final String cardNumber; // card number must be unique
    private final String pin; // and of course pin too
    private int balance = 0; // initialize balance with 0
    private static int numberOfAccount = 0;
    Random random = new Random();

    public Account() { // when instantiated, card number and pin must be created
        cardNumber = createCardNumber();
        pin = createPin();
        numberOfAccount++;
    }

    private String createPin() { // create 4 digit pin number
        return String.format("%04d", random.nextInt(10_000));
    }

    private String createCardNumber() { // create 9 digit account Identifier number, check bit
        // concatenate all to create full card number
        String accountIdentifierNumber = String.format("%09d", random.nextInt(1_000_000_000));
        int checkSum = generateCheckSum((BIN + accountIdentifierNumber));
        return BIN + accountIdentifierNumber + checkSum;
    }

    private int generateCheckSum(String accountIdentifierNumber) { // this method generate the check sum bit according to card number (except last digit, which is check bit)
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int multiple;
            int currentDigit = Integer.parseInt(String.valueOf(accountIdentifierNumber.charAt(i))); // make sure that this digit is exact digit, not the ASCII decimal code
            if (i % 2 == 0) {
                multiple = currentDigit * 2;
                if (multiple > 9) {
                    sum += (multiple - 9);
                } else {
                    sum += multiple;
                }
            } else {
                sum += currentDigit;
            }
        }
        return 10 - (sum % 10) == 10 ? 0 : 10 - (sum % 10); // if the check sum bit equals 10, then return 0, otherwise subtract from 10 and return remain
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

    public static int getNumberOfAccount() {
        return numberOfAccount;
    }
}
