package contacts;

import java.util.ArrayList;

public class PhoneBook {
    private final ArrayList<Contact> phoneBooks;

    public PhoneBook() {
        this.phoneBooks = new ArrayList<>();
    }

    public boolean addPhoneBook (Contact contact) {
        phoneBooks.add(contact);
        return true;
    }

    public int lengthOfPhoneBook() {
        return phoneBooks.size();
    }
}
