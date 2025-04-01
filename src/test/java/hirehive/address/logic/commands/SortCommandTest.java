package hirehive.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.testutil.TypicalPersons;

public class SortCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortCommand_sortsApplicantsByDate() {
        SortCommand sortCommand = new SortCommand();

        // Execute sorting on both models
        sortCommand.execute(model);
        expectedModel.sortPersons();

        // Check if both models are now sorted in the same way
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
