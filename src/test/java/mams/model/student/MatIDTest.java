package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.testutil.Assert;

public class MatIDTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MatID(null));
    }

    @Test
    public void constructor_invalidMatID_throwsIllegalArgumentException() {
        String invalidMatID = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MatID(invalidMatID));
    }

    @Test
    public void isValidMatID() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> MatID.isValidMatID(null));

        // blank email
        assertFalse(MatID.isValidMatID("")); // empty string
        assertFalse(MatID.isValidMatID(" ")); // spaces only

        // missing parts
        //todo
    }
}
