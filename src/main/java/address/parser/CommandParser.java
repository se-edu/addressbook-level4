package address.parser;

/**
 *
 */
public class CommandParser {



    public static boolean isCommandInput(String input) {
        String firstWord = input.split(" ")[0].toLowerCase();
        return firstWord.equals("add") || firstWord.equals("delete") || firstWord.equals("edit");
    }

    public static Command parse(String input) {

        if (!isCommandInput(input)) {
            throw new IllegalArgumentException("Invalid command input");
        }

        String firstWord = input.split(" ")[0].toLowerCase();

        switch (firstWord) {
            case "add" :
                return new AddCommand();
            case "delete" :
                return new DeleteCommand();
            default:
                return null;
        }
    }
}
