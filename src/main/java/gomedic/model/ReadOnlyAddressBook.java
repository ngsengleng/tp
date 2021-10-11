package gomedic.model;

import gomedic.model.activity.Activity;
import gomedic.model.person.Person;
import gomedic.model.person.doctor.Doctor;
import gomedic.model.person.patient.Patient;
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
     * Returns an unmodifiable view of the doctors list.
     * Guarantee: This list will not contain any duplicate doctors.
     */
    ObservableList<Doctor> getDoctorListSortedById();

    /**
     * Returns an unmodifiable view of the patients list.
     * Guarantee: This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientListSortedById();

    /**
     * Returns an unmodifiable view of the activity list.
     * Guarantee: This list will not contain any conflicting and duplicate activity.
     */
    ObservableList<Activity> getActivityListSortedById();

    /**
     * Returns a sorted list by start time.
     * Guarantee: This list will not contain any conflicting and duplicate activity.
     */
    ObservableList<Activity> getActivityListSortedStartTime();
}
