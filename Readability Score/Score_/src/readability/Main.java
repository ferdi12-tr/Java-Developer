package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String pathToFile = args[0]; // enter the file path in command line argument
        File file = new File(pathToFile); // create a file object according to file path

        String fullText = ""; // to get all file context

        try(Scanner scanner = new Scanner(file)) { // try with resources
            fullText += scanner.nextLine();  // accumulate line by line
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        int numberOfSentence = fullText.split("\\w+[.!?]").length; // find the number of sentences.
        int numberOfWord = fullText.split("\\s").length; // find the number of word.
        int numberOfCharacter = fullText.replaceAll(" |\n|\t","").split("").length; // find the number of character

        double score = 4.71 * numberOfCharacter / numberOfWord
                + 0.5* numberOfWord / numberOfSentence
                - 21.43;  // calculate the score according to descriptions

        int index = (int) Math.ceil(score); // we round the score so that we can get the right readability index
        String readability = readabilityIndex(index); // pass the right index to get right sentence

        System.out.println("The text is:\n" + fullText
                + "\n" + "Words: " + numberOfWord
                + "\n" + "Sentences: " + numberOfSentence
                + "\n" + "Characters: " + numberOfCharacter
                + "\n" + "The score is: " + String.valueOf(score).substring(0,4) // print two digit after the dot
                + "\n" + readability);

    }

    private static String readabilityIndex(int index) { // return the sentence according to age(that is score)
         // to get right case according to table from https://en.wikipedia.org/w/index.php?title=Automated_readability_index&diff=1011143677&oldid=856199533
        switch (index) {
            case 1:
            case 2:
                return "This text should be understood by " + (index + 4) + "-" + (index + 5) + "-year-olds.";
            case 3:
                return "This text should be understood by " + (index + 4) + "-" + (index + 6) + "-year-olds.";
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return "This text should be understood by " + (index + 5) + "-" + (index + 6) + "-year-olds.";
            case 13:
                return "This text should be understood by " + (index + 5) + "-" + (index + 11) + "-year-olds.";
            case 14:
                return "This text should be understood by " + 24 + "+-year-olds.";
            default:
                return "";
        }

    }
}
