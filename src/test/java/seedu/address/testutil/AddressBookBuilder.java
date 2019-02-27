package seedu.address.testutil;

import seedu.address.model.FinanceTracker;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FinanceTracker ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FinanceTracker financeTracker;

    public AddressBookBuilder() {
        financeTracker = new FinanceTracker();
    }

    public AddressBookBuilder(FinanceTracker financeTracker) {
        this.financeTracker = financeTracker;
    }

    /**
     * Adds a new {@code Person} to the {@code FinanceTracker} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        financeTracker.addPerson(person);
        return this;
    }

    public FinanceTracker build() {
        return financeTracker;
    }
}
