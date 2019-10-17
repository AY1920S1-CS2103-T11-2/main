package mams.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for Student class */
    public static final Prefix PREFIX_STUDENT = new Prefix("e/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CREDITS = new Prefix("c/");
    public static final Prefix PREFIX_PREVMODS = new Prefix("p/");
    public static final Prefix PREFIX_MATRICID = new Prefix("m/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for Module class */
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("d/");
    public static final Prefix PREFIX_SESSIONID = new Prefix("s/");

    /*Prefix definitions for Appeal class */
    public static final Prefix PREFIX_REASON = new Prefix("rr/");
    public static final Prefix PREFIX_APPEALID = new Prefix("a/");


}
