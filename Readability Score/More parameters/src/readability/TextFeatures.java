package readability;

public class TextFeatures {
    private String text;
    private int numberOfSentence;
    private int numberOfWord;
    private int numberOfCharacter;
    private int numberOfSyllable;
    private int numberOfPolysyllables;

    public TextFeatures(String text) {
        this.text = text;
        this.numberOfSentence = text.split("\\w+[.!?]").length; // find the number of sentences.;
        this.numberOfWord = text.split("\\s").length; // find the number of word.
        this.numberOfCharacter = text.replaceAll(" |\n|\t", "").split("").length; // find the number of character

        String fullText = generateTextVowel(text.toLowerCase()); // prepare text for counting vowel
        this.numberOfSyllable = calculateSyllables(fullText); // calculate how many syllables (that is how many vowels) we have in fullText

        String[] fullTextWords = fullText.split("\\s"); // we have to go through words instead of character
        this.numberOfPolysyllables = calculatePolysllables(fullTextWords);
    }

    public String generateTextVowel(String fullText) { //get rid of some restrictions in order to make syllables counting easy
        fullText = fullText.replaceAll("e\\b", ""); // If the last letter in the word is 'e' do not count it as a vowel (for example, "side" has 1 syllable).
        fullText = fullText.replaceAll("[aeiouy]{2,}", "a"); //Do not count double-vowels (for example, "rain" has 2 vowels but only 1 syllable).
        fullText = fullText.replaceAll("\\s[^aeiouy]+\\s", " a "); //At the end, if it turns out that the word contains 0 vowels, then consider this word as a 1-syllable one.
        fullText = fullText.replaceAll("[0-9]+", "a"); // change any number into a vowel
        return fullText;
    }

    public int calculateSyllables(String fullText) { // we count syllables by counting vowels
        int countOfVowel = 0; // initialize for counting vowel we have in fullText
        String vowel = "aeiouy"; // the string containing vowels
        char[] chars = fullText.toCharArray(); //from string to char array

        for(int i = 0; i < chars.length; i++) { // iterate over the char array therefore we can calculate amount of vowels
            if (vowel.contains(String.valueOf(chars[i]))) { //evaluate every vowel we have in char array
                countOfVowel++;
            }
        }
        return countOfVowel;
    }

    public int calculatePolysllables(String[] fullTextWords) { // count polysyllables by counting words with more than 2 syllables
        char[] chars;
        String vowel = "aeiouy";
        int count = 0; // how many vowels we have in an individual word
        int numberOfPolysllables = 0;  // the number of polysllables

        for (int i = 0; i < fullTextWords.length; i++) { // go through words
            chars = fullTextWords[i].toCharArray();

            for (int j = 0; j <chars.length; j++) { // go through character of corresponding word
                if(vowel.contains(String.valueOf(chars[j]))) {
                    count++;
                }
            }
            if (count >= 3) {
                numberOfPolysllables++;
            }
            count = 0;
        }
        return numberOfPolysllables;
    }

    public String getText() {
        return text;
    }

    public int getNumberOfSentence() {
        return numberOfSentence;
    }

    public int getNumberOfWord() {
        return numberOfWord;
    }

    public int getNumberOfCharacter() {
        return numberOfCharacter;
    }

    public int getNumberOfSyllable() {
        return numberOfSyllable;
    }

    public int getNumberOfPolysyllables() {
        return numberOfPolysyllables;
    }
}
