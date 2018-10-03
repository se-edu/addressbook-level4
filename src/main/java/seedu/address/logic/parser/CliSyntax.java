package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_EMPLOYEEID = new Prefix("id/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATEOFBIRTH = new Prefix("dob/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_DEPARTMENT = new Prefix("d/");
    public static final Prefix PREFIX_POSITION = new Prefix("r/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SALARY = new Prefix("s/");
    public static final Prefix PREFIX_BONUS = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_EMPLOYEE_EXPENSES_ID = new Prefix("eeid/");
    public static final Prefix PREFIX_EXPENSES_AMOUNT = new Prefix("ex/");

    public static final Prefix PREFIX_SCHEDULE_DATE = new Prefix("d/");
    public static final Prefix PREFIX_SCHEDULE_TYPE = new Prefix("t/");

    public static final Prefix PREFIX_JOB_POSITION = new Prefix("jp/");
    public static final Prefix PREFIX_MINIMUM_EXPERIENCE = new Prefix("me/");
    public static final Prefix PREFIX_JOB_DESCRIPTION = new Prefix("jd/");
}
