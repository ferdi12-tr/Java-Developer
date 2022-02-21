package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// add, remove, edit, count, list, exit
public enum Action {
    ADD("add"),
    REMOVE("remove"),
    EDIT("edit"),
    COUNT("count"),
    LIST("list"),
    EXIT("exit"),
    NAME("name"),
    SURNAME("surname"),
    NUMBER("number");

    private final String action;


    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static boolean checkValidityPhoneNumber(String number) {
        return checkValidity(number); // if we satisfied the rule of parentheses, then this will be true
    }

    private static boolean checkValidity(String number) {
        String[] strArr = number.split("[\\s-]"); // split grouped number into separate grouped number group
        boolean checkParentheses = true; // to control if the parenthesis rule is satisfied of not
        boolean checkSymbols = true; // to check if all group is satisfied the rule of symbols
        int countParentheses = 0; // we also need to count the parenthesis

        for (int i = 0; i < strArr.length; i++) {
            Pattern p = Pattern.compile("^\\+?\\(\\+?[\\w]+\\)$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(strArr[i]);
            if (m.find()) { // there are parenthesis
                if (i > 1) { // if parenthesis shows up neither of index 0 nor 1, this means the wrong index
                    checkParentheses = false;
                } else { // otherwise, we have parenthesis and we must count them
                    countParentheses++;
                }
            } else {
                if (i == 0) {
                    // the first group might include only one symbol
                    checkSymbols = strArr[i].matches("^\\+?[\\w]+$");
                } else {
                    // but the others must include at least two symbol
                    checkSymbols = strArr[i].matches("^[\\w]{2,}$");
                }
                if (!checkSymbols) { // if any group doesn't satisfy symbol rule, then stop loop and make sure checkSymbols equals to false
                    break;
                }
            }
        }
        return checkParentheses && countParentheses < 2 && checkSymbols;
    }
}