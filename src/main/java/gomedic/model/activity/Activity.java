package gomedic.model.activity;

import java.util.Objects;
import java.util.function.Predicate;

import gomedic.commons.util.AppUtil;
import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Time;

/**
 * Represents an Activity in the address book.
 * Guarantees: immutable; details are present and not null, all fields are validated and immutable.
 * Is valid as declared in {@link #isValidActivity(Time, Time)}.
 */
public class Activity {

    public static final String MESSAGE_CONSTRAINTS =
            "Start time must be before end time";

    // Identity fields
    private final ActivityId activityId;

    // Data fields
    private final Time startTime;
    private final Time endTime;
    private final Title title;
    private final Description description;

    /**
     * Constructs a {@code Activity} class
     * Every field must be present and not null.
     * startTime must be before endTime strictly (not equal).
     */
    public Activity(ActivityId activityId,
                    Time startTime,
                    Time endTime,
                    Title title,
                    Description description) {
        CollectionUtil.requireAllNonNull(activityId, startTime, endTime, title, description);
        AppUtil.checkArgument(isValidActivity(startTime, endTime), MESSAGE_CONSTRAINTS);
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    public ActivityId getActivityId() {
        return activityId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * @return hashcode in the order of id, start time, end time, desc, title.
     */
    @Override
    public int hashCode() {
        return Objects.hash(activityId, startTime, endTime, description, title);
    }

    /**
     * Equality would depend on the unique id only and not the other fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.activityId.equals(activityId);
    }

    @Override
    public String toString() {
        return activityId
                + "; Title: " + title
                + "; Desc: " + description
                + "; Start Time: " + startTime
                + "; End Time: " + endTime;
    }

    /**
     * Checks whether the other activity is conflicting with the current activity.
     *
     * @param other Activity.
     * @return true if its conflicting (meaning there is full/partial overlap of the activity timing).
     */
    public boolean isConflicting(Activity other) {
        Predicate<Time> isWithinThisActPeriod = time -> time.isAfter(startTime) && time.isBefore(endTime);

        boolean isEndTimeEqual = other.endTime.time.isEqual(endTime.time);
        boolean isStartTimeEqual = other.startTime.time.isEqual(startTime.time);
        boolean isOtherEndTimeOverlapping = isWithinThisActPeriod.test(other.endTime) || isEndTimeEqual;
        boolean isOtherStartTimeOverlapping = isWithinThisActPeriod.test(other.startTime) || isStartTimeEqual;

        boolean isConflictingOverlap = (other.endTime.isAfter(endTime)
                || isEndTimeEqual)
                && (other.startTime.isBefore(startTime)
                || isStartTimeEqual);

        return isOtherStartTimeOverlapping || isOtherEndTimeOverlapping || isConflictingOverlap;
    }

    /**
     * Returns true iff startTime < endTime.
     */
    private boolean isValidActivity(Time startTime, Time endTime) {
        return startTime.isBefore(endTime);
    }
}