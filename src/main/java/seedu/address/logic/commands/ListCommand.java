package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonListPanel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons in the address book.\n"
            + "Parameters: [sort]\n"
            + "Example: " + COMMAND_WORD + " sort";

    private final boolean shouldSort;

    public ListCommand(boolean shouldSort) {
        this.shouldSort = shouldSort;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (shouldSort) {
            PersonListPanel.setComparator(
                    Comparator.comparing(p -> p.getName().toString())
            );
        } else {
            PersonListPanel.setComparator(null);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
