package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Organization extends Contact {
    private String organizationName;
    private String address;

    public Organization(String organizationName, String address, String number) {
        super(number);
        this.organizationName = organizationName;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.organizationName;
    }

    @Override
    public String printInfo() {

        String lastEdittedTime = super.getLastEdittedTime().format(DateTimeFormatter.ISO_DATE_TIME);
        String createdTime = super.getCreatedTime().format(DateTimeFormatter.ISO_DATE_TIME);

        return String.format(
                "Organization name: %s\n" +
                "Address: %s\n" +
                "Number: %s\n" +
                "Time created: %s\n" +
                "Time last edit: %s\n", organizationName, address, super.getNumber(),
                createdTime, lastEdittedTime
        );
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        super.setLastEdittedTime(LocalDateTime.now());
    }

    public void setAddress(String address) {
        this.address = address;
        super.setLastEdittedTime(LocalDateTime.now());
    }
}
