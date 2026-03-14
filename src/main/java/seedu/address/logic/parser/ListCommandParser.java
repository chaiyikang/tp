package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand(ListCommand.SortOrder.NONE);
        }

        // Handling more than 1 argument, e.g. "list sort reverse"
        String[] tokens = trimmedArgs.split("\\s+");

        if (!tokens[0].equalsIgnoreCase("sort")) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }

        if (tokens.length == 1) {
            return new ListCommand(ListCommand.SortOrder.ASCENDING);
        }

        // The allowable arguments are EITHER ONE of "ascending", "descending", and "reverse"
        if (tokens.length == 2) {
            String order = tokens[1].toLowerCase();

            switch (order) {
            case "ascending":
                return new ListCommand(ListCommand.SortOrder.ASCENDING);
            case "descending":
            case "reverse":
                return new ListCommand(ListCommand.SortOrder.DESCENDING);
            default:
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        ListCommand.MESSAGE_USAGE));
            }
        }
        throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
