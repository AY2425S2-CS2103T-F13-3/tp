package hirehive.address.testutil;

import hirehive.address.logic.commands.AddCommand;
import hirehive.address.logic.parser.CliSyntax;
import hirehive.address.model.person.Person;

public class DefaultPersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(CliSyntax.PREFIX_ROLE + person.getRole().fullRole + " ");
        return sb.toString();
    }
}

