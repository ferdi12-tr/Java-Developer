package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String pathToFile = args[0]; // enter the file path in command line argument
        File file = new File(pathToFile); // create a file object according to file path

        String text = ""; // to get all file context

        try (Scanner scanner = new Scanner(file)) { // try with resources
            text += scanner.nextLine();  // accumulate line by line
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        TextFeatures textFeatures = new TextFeatures(text);
        print(textFeatures);

    }

    private static void print(TextFeatures textFeatures) { // print the information on screen
        System.out.println("The text is:\n" + textFeatures.getText()
                + "\n" + "Words: " + textFeatures.getNumberOfWord()
                + "\n" + "Sentences: " + textFeatures.getNumberOfSentence()
                + "\n" + "Characters: " + textFeatures.getNumberOfCharacter()
                + "\n" + "Syllables: " + textFeatures.getNumberOfSyllable()
                + "\n" + "Polysyllables: " + textFeatures.getNumberOfPolysyllables());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String wantedScore = scanner.nextLine();
        System.out.println();

        switch (wantedScore) {
            case "ARI":
                // method calculating score of automated readability index and printing corresponding message
                ReadabilityFunctions.automatedReadabilityIndex(
                        textFeatures.getNumberOfWord(), textFeatures.getNumberOfSentence(), textFeatures.getNumberOfCharacter());
                break;
            case "FK":
                // method calculating score of  Flesch–Kincaid readability tests index and printing corresponding message
                ReadabilityFunctions.fleschKincaidReadabilityIndex(
                        textFeatures.getNumberOfWord(), textFeatures.getNumberOfSentence(), textFeatures.getNumberOfSyllable());
                break;
            case "SMOG":
                // method calculating score of   Simple Measure of Gobbledygook(SMOG) index and printing corresponding message
                ReadabilityFunctions.smogIndex(textFeatures.getNumberOfSentence(), textFeatures.getNumberOfPolysyllables());
                break;
            case "CL":
                // method calculating score of   Coleman–Liau index and printing corresponding message
                ReadabilityFunctions.colemanLiauIndex(
                        textFeatures.getNumberOfSentence(), textFeatures.getNumberOfWord(), textFeatures.getNumberOfCharacter());
                break;
            default:  // print all the method
                ReadabilityFunctions.automatedReadabilityIndex(
                        textFeatures.getNumberOfWord(), textFeatures.getNumberOfSentence(), textFeatures.getNumberOfCharacter());

                ReadabilityFunctions.fleschKincaidReadabilityIndex(
                        textFeatures.getNumberOfWord(), textFeatures.getNumberOfSentence(), textFeatures.getNumberOfSyllable());

                ReadabilityFunctions.smogIndex(textFeatures.getNumberOfSentence(), textFeatures.getNumberOfPolysyllables());

                ReadabilityFunctions.colemanLiauIndex(
                        textFeatures.getNumberOfSentence(), textFeatures.getNumberOfWord(), textFeatures.getNumberOfCharacter());
                break;
        }
    }

}




