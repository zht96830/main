package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.person.Expense;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate expense(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyFinanceTracker source) {
        persons.addAll(source.getFinanceList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Expense expense = jsonAdaptedPerson.toModelType();
            if (financeTracker.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            financeTracker.addExpense(expense);
        }
        return financeTracker;
    }

}
