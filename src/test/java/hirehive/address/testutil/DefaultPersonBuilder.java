package hirehive.address.testutil;

import java.util.HashSet;
import java.util.Set;

import hirehive.address.model.person.Address;
import hirehive.address.model.person.Email;
import hirehive.address.model.person.Name;
import hirehive.address.model.person.Person;
import hirehive.address.model.person.Phone;
import hirehive.address.model.person.Role;
import hirehive.address.model.tag.Tag;

/**
 * A utility class to represent default persons added.
 */
public class DefaultPersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ROLE = "Software Engineer";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Role role;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public DefaultPersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        role = new Role(DEFAULT_ROLE);
    }

    public Person build() {
        return Person.createDefaultPerson(name, phone, email, address, role);
    }
}

