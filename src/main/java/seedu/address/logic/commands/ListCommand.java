package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_SUCCESS_SORT = "Listed all persons in alphabetical order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons in the address book.\n"
            + "Parameters: [sort]\n"
            + "Example: " + COMMAND_WORD + " sort";

    private final boolean shouldSort;

    private final Logger logger = LogsCenter.getLogger(getClass());

    public ListCommand(boolean shouldSort) {
        this.shouldSort = shouldSort;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // TODO: Implement sorting - may require editing PersonListPanel to allow sort/comparator
        if (shouldSort) {
            logger.info("TODO: Sorting the FilteredList (view only) of persons in the address book.");
            return new CommandResult(MESSAGE_SUCCESS_SORT);
        } else {
            logger.info("TODO: No sorting to be done.");
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
