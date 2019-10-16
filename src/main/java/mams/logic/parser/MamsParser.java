package mams.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mams.commons.core.Messages;
import mams.logic.commands.*;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MamsParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ClashCommand.COMMAND_WORD:
            return new ClashCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ApproveCommand.COMMAND_WORD:
            return new ApproveCommandParser().parse(arguments);
            case RejectCommand.COMMAND_WORD:
                return new RejectCommandParser().parse(arguments);
        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
