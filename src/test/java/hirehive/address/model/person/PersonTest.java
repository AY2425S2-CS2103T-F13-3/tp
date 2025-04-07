package hirehive.address.model.person;

import static hirehive.address.logic.commands.CommandTestUtil.DEFAULT_TAG_APPLICANT;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static hirehive.address.logic.commands.CommandTestUtil.VALID_TAG_CANDIDATE;
import static hirehive.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hirehive.address.testutil.Assert;
import hirehive.address.testutil.PersonBuilder;
import hirehive.address.testutil.TypicalPersons;

public class PersonTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRole(VALID_ROLE_BOB)
                .withTag(VALID_TAG_CANDIDATE).withNote(VALID_NOTE_BOB).withDate(VALID_DATE_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(TypicalPersons.BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        Assertions.assertTrue(TypicalPersons.BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(TypicalPersons.BOB).withName(nameWithTrailingSpaces).build();
        Assertions.assertTrue(TypicalPersons.BOB.isSamePerson(editedBob));

        //name same, only phone different, all other attributes same -> returns false
        Person editedAmy = new PersonBuilder(TypicalPersons.AMY).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRole(VALID_ROLE_AMY)
                .withTag(DEFAULT_TAG_APPLICANT).withNote(VALID_NOTE_AMY).build();
        Assertions.assertFalse(TypicalPersons.AMY.isSamePerson(editedAmy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(TypicalPersons.ALICE).build();
        Assertions.assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(5));

        // different person -> returns false
        Assertions.assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(VALID_ADDRESS_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different Tag -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTag(VALID_TAG_CANDIDATE).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different note -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withNote(VALID_NOTE_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withDate(VALID_DATE_BOB).build();
        Assertions.assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + TypicalPersons.ALICE.getName() + ", phone=" + TypicalPersons.ALICE.getPhone()
                + ", email=" + TypicalPersons.ALICE.getEmail() + ", address=" + TypicalPersons.ALICE.getAddress() + ", role=" + TypicalPersons.ALICE.getRole()
                + ", tag=" + TypicalPersons.ALICE.getTag() + ", note=" + TypicalPersons.ALICE.getNote() + ", "
                + "interviewDate=" + TypicalPersons.ALICE.getDate() + "}";
        Assertions.assertEquals(expected, TypicalPersons.ALICE.toString());
    }
}
