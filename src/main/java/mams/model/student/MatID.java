package mams.model.student;

import static java.util.Objects.requireNonNull;

import mams.commons.util.AppUtil;

/**
 * Represents a Student's MatID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatID(String)}
 */
public class MatID {

    public static final String MESSAGE_CONSTRAINTS = "MatID should be of the format A01234567B ";

    public final String value;

    /**
     * Constructs an {@code MatID}.
     *
     * @param matid A valid MatID.
     */
    public MatID(String matid) {
        requireNonNull(matid);
        AppUtil.checkArgument(isValidMatID(matid), MESSAGE_CONSTRAINTS);
        value = matid;
    }

    /**
     * Returns if a given string is a valid email.
     */
    //todo
    public static boolean isValidMatID(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatID // instanceof handles nulls
                && value.equals(((MatID) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
