package contacts;

import java.time.LocalDateTime;

interface Builder {
    void setName(String name);
    void setSurname(String surname);
    void setNumber(String number);
    void setOrganizationName(String organizationName);
    void setAddress(String address);
    void setBirthDay(String birthDay);
    void setGender(String gender);

    Contact getPerson();
    Contact getOrganization();
}