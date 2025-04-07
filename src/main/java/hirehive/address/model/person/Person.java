package hirehive.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import hirehive.address.commons.util.CollectionUtil;
import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Role role;
    private final Tag tag;
    private final Note note;
    private final InterviewDate date;

    /**
     * Every field must be present.
     */
    public Person(Name name, Phone phone, Email email, Address address, Role role, Tag tag, Note note,
                  InterviewDate date) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, role, tag, note);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.tag = tag;
        this.note = note;
        this.date = date;
    }

    /**
     * Method used to construct a default Person object without a note.
     */
    public static Person createDefaultPerson(Name name, Phone phone, Email email, Address address, Role role) {
        return new Person(name, phone, email, address, role, Tag.getDefaultTag(), new Note(Note.DEFAULT_NOTE),
                new InterviewDate());
    }

    /**
     * Method used to construct a default Person object with a note.
     */
    public static Person addDefaultPersonWithNote(Name name, Phone phone, Email email, Address address, Role role,
                                                  Note note) {
        return new Person(name, phone, email, address, role, Tag.getDefaultTag(), note,
                new InterviewDate());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public Note getNote() {
        return note;
    }

    public InterviewDate getDate() {
        return date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Returns true if both persons have the same name, regardless of whether it is in upper/lower case.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && role.equals(otherPerson.role)
                && tag.equals(otherPerson.tag)
                && note.equals(otherPerson.note)
                && date.equals(otherPerson.date);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, role, tag, note, date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("role", role)
                .add("tag", tag)
                .add("note", note)
                .add("interviewDate", date)
                .toString();
    }

}
