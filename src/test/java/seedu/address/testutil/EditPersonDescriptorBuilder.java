package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.address.model.attributes.Address;
import seedu.address.model.attributes.Email;
import seedu.address.model.expense.Expense;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Amount;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditExpenseDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.EditExpenseDescriptor();
    }

    public EditPersonDescriptorBuilder(EditExpenseDescriptor descriptor) {
        this.descriptor = new EditCommand.EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditPersonDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseDescriptor();
        descriptor.setName(expense.getName());
        descriptor.setAmount(expense.getAmount());
        descriptor.setEmail(expense.getEmail());
        descriptor.setAddress(expense.getAddress());
        descriptor.setTags(expense.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setAmount(new Amount(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditExpenseDescriptor build() {
        return descriptor;
    }
}
