package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.ExpenseBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Chicken"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Chicken", "Rice"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Duck", "Rice"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cHicken", "rICE"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Duck"));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Chicken Rice").build()));

        // Keywords match amount, date and category, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("1.10", "10-03-2019", "food", "FOOD"));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Chicken Rice").withAmount("1.10")
                .withDate("10-03-2019").withCategory("food").build()));
    }
}
