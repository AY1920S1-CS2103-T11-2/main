package mams.model.appeal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.testutil.TypicalAppeals;
public class AppealTest {

    @Test
    public void isSameAppeal() {

        Appeal APPEALTEST1 = TypicalAppeals.APPEAL1;

        // same object -> returns true
        Assertions.assertTrue(APPEALTEST1.isSameAppeal(APPEALTEST1));

        //null -> returns false
        Assertions.assertFalse(APPEALTEST1.isSameAppeal(null));

        // approved appeal -> returns false
        Appeal approveAppeal = new Appeal(APPEALTEST1.getAppealId(),
                APPEALTEST1.getAppealType(),
                APPEALTEST1.getStudentId(),
                APPEALTEST1.getAcademicYear(),
                APPEALTEST1.getStudentWorkload(),
                APPEALTEST1.getAppealDescription(),
                APPEALTEST1.getPreviousModule(),
                APPEALTEST1.getNewModule(),
                APPEALTEST1.getModuleToAdd(),
                APPEALTEST1.getModuleToDrop(),
                true,
                "approved",
                "");
        assertFalse(APPEALTEST1.isSameAppeal(approveAppeal));

        // rejected appeal -> returns false
        Appeal rejectedAppeal = new Appeal(APPEALTEST1.getAppealId(),
                APPEALTEST1.getAppealType(),
                APPEALTEST1.getStudentId(),
                APPEALTEST1.getAcademicYear(),
                APPEALTEST1.getStudentWorkload(),
                APPEALTEST1.getAppealDescription(),
                APPEALTEST1.getPreviousModule(),
                APPEALTEST1.getNewModule(),
                APPEALTEST1.getModuleToAdd(),
                APPEALTEST1.getModuleToDrop(),
                true,
                "rejected",
                "");
        assertFalse(APPEALTEST1.isSameAppeal(rejectedAppeal));

    }

    @Test
    public void equals() {
        Appeal APPEALTOTEST = TypicalAppeals.APPEAL1;

        // same object -> returns true
        assertTrue(APPEALTOTEST.equals(APPEALTOTEST));

        // null -> returns false
        assertFalse(APPEALTOTEST.equals(null));

        // different type -> return false
        assertFalse(APPEALTOTEST.equals(5));

        // different appeal -> return  false
        Appeal ANOTHERAPPEAL = TypicalAppeals.APPEAL2;
        assertFalse(APPEALTOTEST.equals(ANOTHERAPPEAL));

        // different appealID -> return false
        Appeal APPEALWITHDIFFERENTID = new Appeal(TypicalAppeals.APPEAL3.getAppealId(),
                APPEALTOTEST.getAppealType(),
                APPEALTOTEST.getStudentId(),
                APPEALTOTEST.getAcademicYear(),
                APPEALTOTEST.getStudentWorkload(),
                APPEALTOTEST.getAppealDescription(),
                APPEALTOTEST.getPreviousModule(),
                APPEALTOTEST.getNewModule(),
                APPEALTOTEST.getModuleToAdd(),
                APPEALTOTEST.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(APPEALTOTEST.equals(APPEALWITHDIFFERENTID));

        // different appeal type -> returns false
        Appeal APPEALOFANOTHERTYPE = new Appeal(APPEALTOTEST.getAppealId(),
                TypicalAppeals.APPEAL2.getAppealType(),
                APPEALTOTEST.getStudentId(),
                APPEALTOTEST.getAcademicYear(),
                APPEALTOTEST.getStudentWorkload(),
                APPEALTOTEST.getAppealDescription(),
                APPEALTOTEST.getPreviousModule(),
                APPEALTOTEST.getNewModule(),
                APPEALTOTEST.getModuleToAdd(),
                APPEALTOTEST.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(APPEALTOTEST.equals(APPEALOFANOTHERTYPE));

        // different studentID -> returns false
        Appeal APPEALWITHDIFFERENTSTUDENTID = new Appeal(APPEALTOTEST.getAppealId(),
                APPEALTOTEST.getAppealType(),
                TypicalAppeals.APPEAL3.getStudentId(),
                APPEALTOTEST.getAcademicYear(),
                APPEALTOTEST.getStudentWorkload(),
                APPEALTOTEST.getAppealDescription(),
                APPEALTOTEST.getPreviousModule(),
                APPEALTOTEST.getNewModule(),
                APPEALTOTEST.getModuleToAdd(),
                APPEALTOTEST.getModuleToDrop(),
                false,
                "",
                "");
        assertFalse(APPEALTOTEST.equals(APPEALWITHDIFFERENTSTUDENTID));
    }

    @Test void isValidAppealId() {
        //invalid code
        assertFalse(Appeal.isValidAppealId("")); // empty string
        assertFalse(Appeal.isValidAppealId("CS")); // C with another letter only
        assertFalse(Appeal.isValidAppealId("C23")); // less than 6 numbers
        assertFalse(Appeal.isValidAppealId("C1233333314")); // more than 6 numbers

        //valid code
        assertTrue(Appeal.isValidAppealId("C000002"));
        assertTrue(Appeal.isValidAppealId("C123456"));
        assertTrue(Appeal.isValidAppealId("C123232"));


    }

    @Test void isValidAppealType() {
        //invalid code
        assertFalse(Appeal.isValidAppealType("")); // empty string
        assertFalse(Appeal.isValidAppealType("addmodule")); // no spaces
        assertFalse(Appeal.isValidAppealType("drop  module")); // more than 1 space
        assertFalse(Appeal.isValidAppealType("increase workload2")); // with numbers

        //valid code
        assertTrue(Appeal.isValidAppealType("add MOdule"));
        assertTrue(Appeal.isValidAppealType("DroP mOdUle"));
        assertTrue(Appeal.isValidAppealType("INCREASE WORKLOAD"));
    }

    @Test
    public void isEqual() {
        assertFalse(Appeal.isValidAppealType("")); // empty string
        assertFalse(Appeal.isValidAppealType("addmodule")); // no spaces
        assertFalse(Appeal.isValidAppealType("drop  module")); // more than 1 space
        assertFalse(Appeal.isValidAppealType("increase workload2")); // with numbers

        //valid code
        assertTrue(Appeal.isValidAppealType("add MOdule"));
        assertTrue(Appeal.isValidAppealType("DroP mOdUle"));
        assertTrue(Appeal.isValidAppealType("INCREASE WORKLOAD"));
    }
}
