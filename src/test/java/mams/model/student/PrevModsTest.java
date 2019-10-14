package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.testutil.Assert;

public class PrevModsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrevMods(null));
    }

    @Test
    public void constructor_invalidPrevMods_throwsIllegalArgumentException() {
        String invalidPrevMods = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrevMods(invalidPrevMods));
    }

    @Test
    public void isValidPrevMods() {
        // null prevMods
        Assert.assertThrows(NullPointerException.class, () -> PrevMods.isValidPrevMod(null));

        // validPrevmods
        assertTrue(PrevMods.isValidPrevMod("CS2019, CS2330"));
    }
}
