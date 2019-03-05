package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalExpenses.EXPENSE;
import static seedu.address.testutil.TypicalExpenses.GROCERIES;
import static seedu.address.testutil.TypicalExpenses.JAPAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FinanceTrackerBuilder;

public class VersionedFinanceTrackerTest {

    private final ReadOnlyFinanceTracker financeTrackerWithExpense = new FinanceTrackerBuilder().withExpense(
            EXPENSE).build();
    private final ReadOnlyFinanceTracker financeTrackerWithJapan = new FinanceTrackerBuilder().withExpense(
            JAPAN).build();
    private final ReadOnlyFinanceTracker financeTrackerWithGroceries = new FinanceTrackerBuilder().withExpense(
            GROCERIES).build();
    private final ReadOnlyFinanceTracker emptyFinanceTracker = new FinanceTrackerBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(emptyFinanceTracker);

        versionedFinanceTracker.commit();
        assertAddressBookListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                emptyFinanceTracker,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        versionedFinanceTracker.commit();
        assertAddressBookListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan),
                financeTrackerWithJapan,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        versionedFinanceTracker.commit();
        assertAddressBookListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                emptyFinanceTracker,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        assertTrue(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        assertTrue(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(emptyFinanceTracker);

        assertFalse(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        assertFalse(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        assertTrue(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        assertTrue(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(emptyFinanceTracker);

        assertFalse(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        assertFalse(versionedFinanceTracker.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        versionedFinanceTracker.undo();
        assertAddressBookListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                financeTrackerWithExpense,
                Collections.singletonList(financeTrackerWithJapan));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        versionedFinanceTracker.undo();
        assertAddressBookListStatus(versionedFinanceTracker,
                Collections.emptyList(),
                emptyFinanceTracker,
                Arrays.asList(financeTrackerWithExpense, financeTrackerWithJapan));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(emptyFinanceTracker);

        assertThrows(VersionedFinanceTracker.NoUndoableStateException.class, versionedFinanceTracker::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        assertThrows(VersionedFinanceTracker.NoUndoableStateException.class, versionedFinanceTracker::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        versionedFinanceTracker.redo();
        assertAddressBookListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense),
                financeTrackerWithJapan,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        versionedFinanceTracker.redo();
        assertAddressBookListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                financeTrackerWithExpense,
                Collections.singletonList(financeTrackerWithJapan));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(emptyFinanceTracker);

        assertThrows(VersionedFinanceTracker.NoRedoableStateException.class, versionedFinanceTracker::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        assertThrows(VersionedFinanceTracker.NoRedoableStateException.class, versionedFinanceTracker::redo);
    }

    @Test
    public void equals() {
        VersionedFinanceTracker versionedFinanceTracker = prepareAddressBookList(financeTrackerWithExpense,
                financeTrackerWithJapan);

        // same values -> returns true
        VersionedFinanceTracker copy = prepareAddressBookList(financeTrackerWithExpense,
                financeTrackerWithJapan);
        assertTrue(versionedFinanceTracker.equals(copy));

        // same object -> returns true
        assertTrue(versionedFinanceTracker.equals(versionedFinanceTracker));

        // null -> returns false
        assertFalse(versionedFinanceTracker.equals(null));

        // different types -> returns false
        assertFalse(versionedFinanceTracker.equals(1));

        // different state list -> returns false
        VersionedFinanceTracker differentAddressBookList = prepareAddressBookList(financeTrackerWithJapan,
                financeTrackerWithGroceries);
        assertFalse(versionedFinanceTracker.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedFinanceTracker differentCurrentStatePointer = prepareAddressBookList(
                financeTrackerWithExpense, financeTrackerWithJapan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);
        assertFalse(versionedFinanceTracker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFinanceTracker} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFinanceTracker#currentStatePointer} is equal to
     * {@code expectedStatesBeforePointer},
     * and states after {@code versionedFinanceTracker#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedFinanceTracker versionedFinanceTracker,
                                             List<ReadOnlyFinanceTracker> expectedStatesBeforePointer,
                                             ReadOnlyFinanceTracker expectedCurrentState,
                                             List<ReadOnlyFinanceTracker> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FinanceTracker(versionedFinanceTracker), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFinanceTracker.canUndo()) {
            versionedFinanceTracker.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFinanceTracker expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new FinanceTracker(versionedFinanceTracker));
            versionedFinanceTracker.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFinanceTracker expectedAddressBook : expectedStatesAfterPointer) {
            versionedFinanceTracker.redo();
            assertEquals(expectedAddressBook, new FinanceTracker(versionedFinanceTracker));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFinanceTracker.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFinanceTracker.undo());
    }

    /**
     * Creates and returns a {@code VersionedFinanceTracker} with the {@code addressBookStates} added into it, and the
     * {@code VersionedFinanceTracker#currentStatePointer} at the end of list.
     */
    private VersionedFinanceTracker prepareAddressBookList(ReadOnlyFinanceTracker... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedFinanceTracker versionedFinanceTracker = new VersionedFinanceTracker(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedFinanceTracker.resetData(addressBookStates[i]);
            versionedFinanceTracker.commit();
        }

        return versionedFinanceTracker;
    }

    /**
     * Shifts the {@code versionedFinanceTracker#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFinanceTracker versionedFinanceTracker, int count) {
        for (int i = 0; i < count; i++) {
            versionedFinanceTracker.undo();
        }
    }
}
