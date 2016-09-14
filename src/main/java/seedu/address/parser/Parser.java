package seedu.address.parser;

import seedu.address.commands.*;
import seedu.address.controller.Ui;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.Tag;
import seedu.address.model.UniqueTagList;
import seedu.address.model.person.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private Ui ui;

    public Parser() {}

    /**
     * Configures the parser with additional dependencies such as Ui
     * @param ui
     */
    public void configure(Ui ui) {
        this.ui = ui;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

            case AddPersonCommand.COMMAND_WORD:
                return prepareAdd(arguments);

            case SelectPersonCommand.COMMAND_WORD:
                return prepareSelect(arguments);

            case DeletePersonCommand.COMMAND_WORD:
                return prepareDelete(arguments);

            case ClearAddressBookCommand.COMMAND_WORD:
                return new ClearAddressBookCommand();

            case FindPersonsByWordsInNameCommand.COMMAND_WORD:
                return prepareFind(arguments);

            case ListAllPersonsCommand.COMMAND_WORD:
                return new ListAllPersonsCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                return new IncorrectCommand("Invalid command");
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException containing a message with relevant info if the args could no be parsed
     */
    private Command prepareAdd(String args){
        final Matcher matcher = AddPersonCommand.ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }
        try {
            final Name name = new Name(matcher.group("name"));
            final Phone phone = new Phone(matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")));
            final Email email = new Email(matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")));
            final Address address = new Address(matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")));

            return new AddPersonCommand(new Person(name, phone, email, address,
                    getTagsFromArgs(matcher.group("tagArguments"))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     */
    private static UniqueTagList getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return new UniqueTagList();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));

        // merge duplicate tags
        final Set<Tag> tags = new HashSet<>();
        for (String tagString : tagStrings) {
            tags.add(new Tag(tagString));
        }
        // tag list prepared
        return new UniqueTagList(tags);
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException containing a message with relevant info if the args could no be parsed
     */
    private Command prepareDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DeletePersonCommand(this.ui.getDisplayedPersons().get(targetIndex - 1));
        } catch (ParseException pe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        DeletePersonCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (IndexOutOfBoundsException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException containing a message with relevant info if the args could no be parsed
     */
    private Command prepareSelect(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            // this check is required to catch any invalid indexes
            this.ui.getDisplayedPersons().get(targetIndex - 1);
            return new SelectPersonCommand(targetIndex);
        } catch (ParseException pe) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        SelectPersonCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (IndexOutOfBoundsException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = TargetLastListedPersonCommand.ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }


    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws ParseException containing a message with relevant info if the args could no be parsed
     */
    private Command prepareFind(String args) {
        final Matcher matcher = FindPersonsByWordsInNameCommand.ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindPersonsByWordsInNameCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindPersonsByWordsInNameCommand(keywordSet);
    }


}