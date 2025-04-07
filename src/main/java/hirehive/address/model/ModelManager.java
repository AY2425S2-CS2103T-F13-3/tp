package hirehive.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import hirehive.address.commons.core.GuiSettings;
import hirehive.address.commons.core.LogsCenter;
import hirehive.address.commons.util.CollectionUtil;
import hirehive.address.model.person.InterviewDate;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Note personNote;
    private final SortedList<Person> sortedPersons;
    private boolean isSorted = false;

    private Predicate<Person> currFilter = PREDICATE_SHOW_ALL_PERSONS;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        personNote = new Note(Note.DEFAULT_NOTE);
        this.sortedPersons = new SortedList<>(filteredPersons);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        unfilterPersonList();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        currFilter = currFilter.and(predicate);
        filteredPersons.setPredicate(currFilter);
        resetSorting();
    }

    @Override
    public void unfilterPersonList() {
        currFilter = PREDICATE_SHOW_ALL_PERSONS;
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        resetSorting();
    }

    @Override
    public void sortPersons() {
        Comparator<Person> comparator = Comparator.comparing(
                person -> person.getDate().getValue().orElse(LocalDate.MAX)
        );
        sortedPersons.setComparator(comparator);
        isSorted = true;
    }

    @Override
    public void resetSorting() {
        sortedPersons.setComparator(null);
        isSorted = false;
    }


    @Override
    public void updatePersonNote(Person person) {
        personNote = person.getNote();
    }

    @Override
    public Note getPersonNote() {
        return personNote;
    }

    @Override
    public int getListSize() {
        return filteredPersons.size();
    }

    /**
     * Returns the next available date for an interview starting from the next day.
     * @return An {@code InterviewDate} object
     */
    @Override
    public InterviewDate getAvailableDate() {
        List<LocalDate> sortedAllDates = this.addressBook.getPersonList().stream()
                .map(person -> person.getDate().getValue().orElse(LocalDate.MAX))
                .sorted(LocalDate::compareTo).toList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate iterationDate = LocalDate.now().plusDays(1);
        boolean bContinue = true;
        int i = 0;
        while (i < sortedAllDates.size() && sortedAllDates.get(i) != LocalDate.MAX && bContinue) {
            LocalDate currDate = sortedAllDates.get(i);
            if (currDate.isBefore(iterationDate)) {
                i++;
            } else if (currDate.isAfter(iterationDate)) {
                bContinue = false;
            } else {
                iterationDate = iterationDate.plusDays(1);
                i++;
            }
        }
        return new InterviewDate(iterationDate.format(formatter));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
