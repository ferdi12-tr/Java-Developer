package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        int numberOfSentence = text.split("\\w+[.!?]").length; // find the number of sentences.
        int numberOfWord = text.split(" ").length; // find the number of word.

        double average = (double) numberOfWord / numberOfSentence;

        if (average > 10) {
            System.out.println("HARD");
        }
        else {
            System.out.println("EASY");
        }
    }
}
