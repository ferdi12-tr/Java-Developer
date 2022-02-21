package readability;

public class ReadabilityFunctions {


    public static void colemanLiauIndex(int numberOfSentence, int numberOfWord, int numberOfCharacter) {

        double l = (double) numberOfCharacter / numberOfWord * 100;
        double s = (double) numberOfSentence / numberOfWord * 100;
        double score = 0.0588 * l - 0.296 * s - 15.8;
        int index = (int) Math.ceil(score); // we round the score so that we can get the right readability index

        System.out.printf("Coleman–Liau index: %.2f %s\n", score, readabilityIndex(index));
    }

    public static void smogIndex(int numberOfSentence, int numberOfPolysyllables) {

        double score = 1.043 * Math.sqrt(numberOfPolysyllables * 30.0 / numberOfSentence) + 3.1291;
        int index = (int) Math.ceil(score); // we round the score so that we can get the right readability index

        System.out.printf("Simple Measure of Gobbledygook: %.2f %s\n", score, readabilityIndex(index));
    }

    public static void fleschKincaidReadabilityIndex(int numberOfWord, int numberOfSentence, int numberOfSyllable) {

        double score = 0.39 * numberOfWord / numberOfSentence  + 11.8 * numberOfSyllable / numberOfWord - 15.59;
        int index = (int) Math.ceil(score); // we round the score so that we can get the right readability index

        System.out.printf("Flesch–Kincaid readability tests: %.2f %s\n", score, readabilityIndex(index));
    }

    // calculate automated readability index and print corresponding string
    public static void automatedReadabilityIndex(int numberOfWord, int numberOfSentence, int numberOfCharacter) {
        double score = 4.71 * numberOfCharacter / numberOfWord
                + 0.5 * numberOfWord / numberOfSentence
                - 21.43;  // calculate the score according to descriptions

        int index = (int) Math.ceil(score); // we round the score so that we can get the right readability index

        System.out.printf("Automated Readability Index: %.2f %s\n", score, readabilityIndex(index));

    }

    private static String readabilityIndex(int index) {
        // to get right age estimate according to table from
        // https://en.wikipedia.org/w/index.php?title=Automated_readability_index&diff=1011143677&oldid=856199533
        switch (index) {
            case 1:
                return "(about 6-year-olds).";
            case 2:
                return "(about 7-year-olds).";
            case 3:
                return "(about 9-year-olds).";
            case 4:
                return "(about 10-year-olds).";
            case 5:
                return "(about 11-year-olds).";
            case 6:
                return "(about 12-year-olds).";
            case 7:
                return "(about 13-year-olds).";
            case 8:
                return "(about 14-year-olds).";
            case 9:
                return "(about 15-year-olds).";
            case 10:
                return "(about 16-year-olds).";
            case 11:
                return "(about 17-year-olds).";
            case 12:
                return "(about 18-year-olds).";
            case 13:
                return "(about 24-year-olds).";
            default:
                return "(about 24+ year-olds).";
        }
    }
}
