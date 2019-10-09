package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mams.model.student.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.testutil.Assert;
import mams.testutil.TypicalStudents;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_MATID = "E009200";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TypicalStudents.BENSON.getName().toString();
    private static final String VALID_PHONE = TypicalStudents.BENSON.getPhone().toString();
    private static final String VALID_MATID = TypicalStudents.BENSON.getMatID().toString();
    private static final String VALID_ADDRESS = TypicalStudents.BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalStudents.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(TypicalStudents.BENSON);
        Assertions.assertEquals(TypicalStudents.BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_MATID, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_PHONE, VALID_MATID,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_MATID, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null,
                VALID_MATID, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidMatID_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_MATID, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = MatID.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                MatID.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_MATID, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_MATID,
                null, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_MATID, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, student::toModelType);
    }

}
