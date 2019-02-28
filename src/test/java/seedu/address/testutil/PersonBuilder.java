package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.attributes.Address;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Email;
import seedu.address.model.expense.Expense;
import seedu.address.model.attributes.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expense objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Amount amount;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code expenseToCopy}.
     */
    public PersonBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        amount = expenseToCopy.getAmount();
        email = expenseToCopy.getEmail();
        address = expenseToCopy.getAddress();
        tags = new HashSet<>(expenseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Expense} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expense} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.amount = new Amount(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Expense} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Expense build() {
        return new Expense(name, amount, email, address, tags);
    }

}
