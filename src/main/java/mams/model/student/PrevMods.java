package mams.model.student;

import static java.util.Objects.requireNonNull;

import mams.commons.util.AppUtil;

/**
 * Represents a Student's Previous Modules in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrevMod(String)}
 */
public class PrevMods {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_CONSTRAINTS = "Prev Mods should be comma seperated values of previously taken modules";
    private static final String MODULES_FORMAT = "[^\\s].*"; // alphanumeric characters except underscore
    public static final String VALIDATION_REGEX = MODULES_FORMAT;

    public final String value;

    /**
     * Constructs an {@code PrevMods}.
     *
     * @param prevMods valid Previous Modules.
     */
    public PrevMods(String prevMods) {
        requireNonNull(prevMods);
        AppUtil.checkArgument(isValidPrevMod(prevMods), MESSAGE_CONSTRAINTS);
        value = prevMods;
    }

    /**
     * Returns if a given string are valid PrevMods.
     */
    public static boolean isValidPrevMod(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrevMods // instanceof handles nulls
                && value.equals(((PrevMods) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
