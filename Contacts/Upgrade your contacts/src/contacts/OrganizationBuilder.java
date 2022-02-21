package contacts;

import java.time.LocalDateTime;

public class OrganizationBuilder implements Builder {

    private String number;
    private String organizationName;
    private String address;

    @Deprecated
    public void setName(String name) {}

    @Deprecated
    public void setSurname(String surname) {}

    @Deprecated
    public Contact getPerson() {return null;}

    @Override
    public void setNumber(String number) {
        if (Action.checkValidityPhoneNumber(number)) {
            this.number = number;
            System.out.print("The record added.\n");
        } else {
            System.out.print("Wrong number format!\n");
            System.out.print("The record added.\n");
            this.number = "[no number]";
        }
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Deprecated
    public void setBirthDay(String birthDay) {}

    @Deprecated
    public void setGender(String gender) {}

    public Organization getOrganization() {
        return new Organization(organizationName, address, number);
    }
}
