package hirehive.address.model;

import static hirehive.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hirehive.address.commons.core.GuiSettings;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import hirehive.address.testutil.AddressBookBuilder;
import hirehive.address.testutil.Assert;
import hirehive.address.testutil.TypicalPersons;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(TypicalPersons.ALICE);
        assertTrue(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void sortPersons_sortsApplicantsByInterviewDate() {
        modelManager.addPerson(TypicalPersons.ELLE);
        modelManager.addPerson(TypicalPersons.BENSON);

        List<Person> originalList = List.copyOf(modelManager.getFilteredPersonList());

        modelManager.sortPersons();

        List<Person> sortedList = modelManager.getFilteredPersonList();

        assertNotEquals(originalList, sortedList);
        assertTrue(sortedList.get(0).getDate().getValue().orElse(LocalDate.MAX)
                .isBefore(sortedList.get(1).getDate().getValue().orElse(LocalDate.MAX)));
    }


    @Test
    public void resetSorting_revertsToOriginalOrder() {
        modelManager.addPerson(TypicalPersons.BENSON);
        modelManager.addPerson(TypicalPersons.ELLE);

        List<Person> originalList = List.copyOf(modelManager.getFilteredPersonList());

        modelManager.sortPersons();
        modelManager.resetSorting();

        List<Person> resetList = modelManager.getFilteredPersonList();

        assertEquals(originalList, resetList);
    }

    @Test
    public void getPersonNote_initial_returnsDefaultNote() {
        assertEquals(modelManager.getPersonNote(), new Note(Note.DEFAULT_NOTE));
    }

    @Test
    public void getPersonNote_person_getPersonNote() {
        modelManager.updatePersonNote(TypicalPersons.ALICE);
        assertEquals(modelManager.getPersonNote(), TypicalPersons.ALICE.getNote());
    }

    @Test
    public void getListSize_initialList_returnListSize() {
        int listSize = modelManager.getListSize();
        assertEquals(listSize, modelManager.getFilteredPersonList().size());
    }

    @Test
    public void getAvailableDate_initialList_returnAvailableDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        assertEquals(modelManager.getAvailableDate(), new InterviewDate(LocalDate.now().plusDays(1).format(formatter)));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(TypicalPersons.ALICE).withPerson(TypicalPersons.BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String keywords = TypicalPersons.ALICE.getName().fullName;
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(keywords));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
