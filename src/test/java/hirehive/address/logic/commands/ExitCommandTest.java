package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import org.junit.jupiter.api.Test;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
