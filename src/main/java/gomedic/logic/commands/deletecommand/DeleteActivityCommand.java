package gomedic.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import gomedic.commons.core.Messages;
import gomedic.logic.commands.Command;
import gomedic.logic.commands.CommandResult;
import gomedic.logic.commands.exceptions.CommandException;
import gomedic.model.Model;
import gomedic.model.activity.Activity;
import gomedic.model.commonfield.Id;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteActivityCommand extends Command {

    public static final String COMMAND_WORD = "delete t/activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index shown in the activity list.\n"
            + "Parameters: INDEX (must be exactly the same as shown in the list, e.g. A001)\n"
            + "Example: " + COMMAND_WORD + " A001";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    private final Id targetId;

    public DeleteActivityCommand(Id targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        Activity personToDelete = lastShownList
                .stream()
                .filter(activity -> activity
                        .getActivityId()
                        .toString().equals(targetId.toString()))
                .findFirst()
                .orElse(null);

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_ID);
        }
        model.deleteActivity(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteActivityCommand // instanceof handles nulls
                && targetId.equals(((DeleteActivityCommand) other).targetId)); // state check
    }
}
