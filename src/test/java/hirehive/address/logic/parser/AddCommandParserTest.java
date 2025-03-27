package hirehive.address.logic.parser;

import org.junit.jupiter.api.Test;

import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.AddCommand;
import hirehive.address.logic.commands.CommandTestUtil;
import hirehive.address.model.person.Address;
import hirehive.address.model.person.Email;
import hirehive.address.model.person.Name;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import hirehive.address.model.person.Phone;
import hirehive.address.model.person.Role;
import hirehive.address.model.tag.Tag;
import hirehive.address.testutil.PersonBuilder;
import hirehive.address.testutil.TypicalPersons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(TypicalPersons.BOB).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.ROLE_DESC_BOB, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.TAG_DESC_APPLICANT + CommandTestUtil.NOTE_DESC_BOB;

        // multiple names
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));

        // multiple phones
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_PHONE));

        // multiple emails
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EMAIL));

        // multiple addresses
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_ADDRESS));

        // multiple notes
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NOTE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NOTE));

        // multiple fields repeated
        CommandParserTestUtil.assertParseFailure(parser,
                validExpectedPersonString + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
                        + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY + CommandTestUtil.ROLE_DESC_AMY
                        + CommandTestUtil.NOTE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_PHONE,
                        CliSyntax.PREFIX_ROLE, CliSyntax.PREFIX_NOTE));

        // invalid value followed by valid value

        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EMAIL));

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_PHONE));

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_ADDRESS));

        // invalid note
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NOTE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NOTE));

        // valid value followed by invalid value

        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME));

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_EMAIL));

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_PHONE));

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_ADDRESS));

        // invalid note
        CommandParserTestUtil.assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NOTE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY
                + CommandTestUtil.ROLE_DESC_AMY + CommandTestUtil.NOTE_DESC_AMY,
                new AddCommand(expectedPerson));

        // no notes
        expectedPerson = new PersonBuilder(TypicalPersons.BOB).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                        + CommandTestUtil.ROLE_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB, expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB, expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB, expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.VALID_ADDRESS_BOB
                + CommandTestUtil.ROLE_DESC_BOB, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.VALID_ADDRESS_BOB
                + CommandTestUtil.VALID_ROLE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.VALID_TAG_CANDIDATE + CommandTestUtil.DEFAULT_TAG_APPLICANT, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.VALID_TAG_CANDIDATE + CommandTestUtil.DEFAULT_TAG_APPLICANT, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.VALID_TAG_CANDIDATE + CommandTestUtil.DEFAULT_TAG_APPLICANT, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.VALID_TAG_CANDIDATE + CommandTestUtil.DEFAULT_TAG_APPLICANT, Address.MESSAGE_CONSTRAINTS);

        // tag was incorrectly given
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_APPLICANT,
                Role.MESSAGE_CONSTRAINTS);

        // invalid note
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC
                        + CommandTestUtil.ROLE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.ROLE_DESC_BOB + CommandTestUtil.VALID_TAG_CANDIDATE + CommandTestUtil.TAG_DESC_APPLICANT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
