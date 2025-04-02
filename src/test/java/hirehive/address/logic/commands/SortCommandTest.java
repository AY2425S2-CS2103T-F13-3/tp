package hirehive.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.model.AddressBook;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.Person;
import hirehive.address.testutil.TypicalPersons;

public class SortCommandTest {

    @Test
    public void execute_sortCommand_sortsApplicantsByDate() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        SortCommand sortCommand = new SortCommand();

        // Execute sorting on both models
        sortCommand.execute(model);
        expectedModel.sortPersons();

        // Check if both models are now sorted in the same way
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortCommand_placesNoInterviewDateAtEnd() {
        Model sortingModel = new ModelManager(new AddressBook(), new UserPrefs());
        sortingModel.addPerson(TypicalPersons.ELLE);
        sortingModel.addPerson(TypicalPersons.ALICE);
        sortingModel.addPerson(TypicalPersons.BENSON);

        SortCommand sortCommand = new SortCommand();
        sortCommand.execute(sortingModel);

        List<Person> sortedList = sortingModel.getFilteredPersonList();

        // Ensure people with interview dates come first
        assertTrue(sortedList.get(0).getDate().getValue().isPresent());
        assertTrue(sortedList.get(1).getDate().getValue().isPresent());
        assertFalse(sortedList.get(2).getDate().getValue().isPresent()); // No date, should be last
    }
}
