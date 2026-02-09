package pl.org.opi.person;

import lombok.Data;

import java.math.*;

@Data
public class PersonSection {
    private BigInteger documentId;
    private BigInteger sectionId;
    private PersonalData personalData;
    private Academic academic;
}
