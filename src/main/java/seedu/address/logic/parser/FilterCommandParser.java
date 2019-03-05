package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.*;
import java.util.concurrent.atomic.AtomicInteger;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FilterCommandParser implements Parser<FilterCommand> {

    private final int TOTAL_NUMBER_OF_INFO = 5;

    private String FilterTypeDivider(String args, AtomicInteger typeOfProcess) {

        typeOfProcess.set(-1);

        if (args.length() > 3 && args.substring(1, 4).equals("or ")) {
             typeOfProcess.set(1);
             args = args.substring(4);
        }

        else if (args.length() > 4 && args.substring(1, 5).equals("and ")) {
             typeOfProcess.set(2);
             args = args.substring(5);
        }

        else if(args.length() > 5 && args.substring(1, 6).equals("clear")) {
            typeOfProcess.set(0);
        }

        return args;
    }

    /** Divides every filter criteria into Strings. The information order in the
     *  returned array is as follows:
     *  1- Name
     *  2- Phone
     *  3- Email
     *  4- Address
     *  5- All tags
     *
     *  !!! If some of the above ones are not given, then their value will be null
     */

    private String[] DivideFilterCriterion(String args, AtomicInteger typeOfProcess) throws ParseException
    {
        String[] criterion = new String[TOTAL_NUMBER_OF_INFO];
        int totalNumOfCriterion = 0;

        if(args.contains("n/") && args.contains("/n")) {
            criterion[0] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[0] = null;
        }

        if(args.contains("p/") && args.contains("/p")) {
            criterion[1] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[1] = null;
        }

        if(args.contains("e/") && args.contains("/e")) {
            criterion[2] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[2] = null;
        }

        if(args.contains("a/") && args.contains("/a")) {
            criterion[3] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[3] = null;
        }

        if(args.contains("t/") && args.contains("/t")) {
            criterion[4] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[4] = null;
        }

        if(totalNumOfCriterion == 0) {
            typeOfProcess.set(-1);
        }

        else {

            if(criterion[0] != null) {
                criterion[0] = InfoBetweenPrefixes(args, "n/", "/n", typeOfProcess);
            }

            if(criterion[1] != null) {
                criterion[1] = InfoBetweenPrefixes(args, "p/", "/p", typeOfProcess);
            }

            if(criterion[2] != null) {
                criterion[2] = InfoBetweenPrefixes(args, "e/", "/e", typeOfProcess);
            }

            if(criterion[3] != null) {
                criterion[3] = InfoBetweenPrefixes(args, "a/", "/a", typeOfProcess);
            }

            if(criterion[4] != null) {
                criterion[4] = InfoBetweenPrefixes(args, "t/", "/t", typeOfProcess);
            }
        }

        return criterion;
    }

    /**
     * Since filter form is like prefix/ text /prefix, this function returns the text between given prefixes.
     */


    private String InfoBetweenPrefixes(String args, String prefixBegin, String prefixEnd, AtomicInteger typeOfProcess) {

        int beginLoc = args.indexOf(prefixBegin);
        int endLoc = args.indexOf(prefixEnd);

        if(beginLoc >= endLoc) {
            typeOfProcess.set(-1);
            return null;
        }

        return args.substring(beginLoc + 2, endLoc).toLowerCase();
    }

    @Override
    public FilterCommand parse(String args) throws ParseException {

        AtomicInteger typeOfProcess = new AtomicInteger(-1);
        args = FilterTypeDivider(args, typeOfProcess);

        String[] criterion = {" ", " ", " ", " ", " "};

        if(typeOfProcess.get() != 0)
            criterion = DivideFilterCriterion(args, typeOfProcess);

        if (typeOfProcess.get() == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        else if (typeOfProcess.get() == 1) {
            return new FilterCommand(args, criterion, 1);
        }
        else if (typeOfProcess.get() == 2) {
            return new FilterCommand(args, criterion, 2);
        }
        else {
            return new FilterCommand(args, criterion, 0);
        }
    }
}
