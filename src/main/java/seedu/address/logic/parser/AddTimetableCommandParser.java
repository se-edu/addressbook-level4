package seedu.address.logic.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;

import java.util.stream.Stream;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.model.person.Name;


public class AddTimetableCommandParser implements Parser<AddTimetableCommand>  {
  /**
   * Parses the given {@code String} of arguments in the context of the AddCommand
   * and returns an AddTimetableCommand object for execution.
   * @throws ParseException if the user input does not conform the expected format
   */
  public AddTimetableCommand parse(String args) throws ParseException {
    ArgumentMultimap argMultimap =
        ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_FILE_NAME, PREFIX_FILE_LOCATION);

    if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_FILE_NAME, PREFIX_FILE_LOCATION)
        || !argMultimap.getPreamble().isEmpty()) {
      throw new ParseException(
          String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE));
    }
    Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    String fileName= ParserUtil.parseFilename(argMultimap.getValue(PREFIX_FILE_NAME).get());
    File ans = new File("");
    String destination = ans.getAbsolutePath().replace("\\","/")+ "/timetableData/"+fileName;
    String location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
    String source = location.replace("-","/")+ "/"+ fileName;
    Path from = Paths.get(source);
    Path to = Paths.get(destination);
    try {
      Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AddTimetableCommand();
  }


  /**
   * Returns true if none of the prefixes contains empty {@code Optional} values in the given
   * {@code ArgumentMultimap}.
   */
  private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
    return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
  }


}
