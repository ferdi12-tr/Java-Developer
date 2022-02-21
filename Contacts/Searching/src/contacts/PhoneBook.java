package contacts;

import java.util.ArrayList;

public class PhoneBook {
    private ArrayList<Contact> phoneBooks; // in order to keep the contacts

    public PhoneBook() { // when initialized, create arraylist
        this.phoneBooks = new ArrayList<>();
    }

    public boolean addPhoneBook (Contact contact) {
        phoneBooks.add(contact);
        return true;
    }

    public int lengthOfPhoneBook() {
        return phoneBooks.size();
    }

    public ArrayList<Contact> getContacts() {
        return phoneBooks;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < lengthOfPhoneBook(); i++) {
            str += String.format("%d. %s\n", i + 1, phoneBooks.get(i).toString());
        }
        return str;
    }

}
