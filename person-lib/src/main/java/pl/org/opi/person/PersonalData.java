package pl.org.opi.person;

import lombok.Data;
import pl.org.opi.common.Address;
import pl.org.opi.common.Contact;
import pl.org.opi.common.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonalData {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String citizenship;
    private List<Address> addresses;
    private Contact contact;
}
