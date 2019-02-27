package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueFinanceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueFinanceList uniqueFinanceList = new UniqueFinanceList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFinanceList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFinanceList.add(ALICE);
        assertTrue(uniqueFinanceList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFinanceList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueFinanceList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueFinanceList.add(ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueFinanceList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueFinanceList.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueFinanceList.add(ALICE);
        uniqueFinanceList.setPerson(ALICE, ALICE);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        expectedUniqueFinanceList.add(ALICE);
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueFinanceList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueFinanceList.setPerson(ALICE, editedAlice);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        expectedUniqueFinanceList.add(editedAlice);
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueFinanceList.add(ALICE);
        uniqueFinanceList.setPerson(ALICE, BOB);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        expectedUniqueFinanceList.add(BOB);
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueFinanceList.add(ALICE);
        uniqueFinanceList.add(BOB);
        thrown.expect(DuplicatePersonException.class);
        uniqueFinanceList.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueFinanceList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueFinanceList.add(ALICE);
        uniqueFinanceList.remove(ALICE);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.setPersons((UniqueFinanceList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueFinanceList.add(ALICE);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        expectedUniqueFinanceList.add(BOB);
        uniqueFinanceList.setPersons(expectedUniqueFinanceList);
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFinanceList.setPersons((List<Person>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueFinanceList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueFinanceList.setPersons(personList);
        UniqueFinanceList expectedUniqueFinanceList = new UniqueFinanceList();
        expectedUniqueFinanceList.add(BOB);
        assertEquals(expectedUniqueFinanceList, uniqueFinanceList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueFinanceList.setPersons(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueFinanceList.asUnmodifiableObservableList().remove(0);
    }
}
