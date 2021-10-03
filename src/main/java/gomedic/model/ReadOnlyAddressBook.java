package gomedic.model;

import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the activity list.
     * Guarantee: This list will not contain any conflicting and duplicate activity.
     */
    ObservableList<Activity> getActivityList();

    /**
     * Returns a sorted list by start time.
     * Guarantee: This list will not contain any conflicting and duplicate activity.
     */
    ObservableList<Activity> getActivityListSortedStartTime();
}
