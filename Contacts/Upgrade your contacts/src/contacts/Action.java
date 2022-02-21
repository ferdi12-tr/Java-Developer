package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// add, remove, edit, count, list, exit
public enum Action {
    ADD("add"),
    REMOVE("remove"),
    EDIT("edit"),
    COUNT("count"),
    INFO("info"),
    EXIT("exit"),
    PERSON("person"),
    ORGANIZATION("organization"),
    NAME("name"),
    SURNAME("surname"),
    NUMBER("number"),
    ADDRESS("address"),
    BIRTH("birth"),
    GENDER("gender");

    private final String action;


    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
    
    public static boolean checkValidityPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^(?i)([+](\\w )?([(]?\\w{2,}[)]?)?|[(]\\w{2,}[)]|\\w{1,}|\\w{2,}[ -][(]\\w{2,}[)])([ -][0-9a-z]{2,})?([ -][0-9a-z]{2,})?([ -][0-9a-z]{2,})?$");
        Matcher matcher = pattern.matcher(number);
        return matcher.find(); // if we satisfied the rule of parentheses, then this will be true
    }
}