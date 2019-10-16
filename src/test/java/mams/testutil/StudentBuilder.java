package mams.testutil;

import java.util.HashSet;
import java.util.Set;

import mams.model.student.Credits;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.PrevMods;
import mams.model.student.Student;
import mams.model.tag.Tag;
import mams.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_CREDITS = "18";
    public static final String DEFAULT_PREVMODS = "alice@gmail.com";
    public static final String DEFAULT_MATRICID = "A0179930Y";

    private Name name;
    private Credits credits;
    private PrevMods prevMods;
    private MatricId matricId;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        credits = new Credits(DEFAULT_CREDITS);
        prevMods = new PrevMods(DEFAULT_PREVMODS);
        matricId = new MatricId(DEFAULT_MATRICID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        credits = studentToCopy.getCredits();
        prevMods = studentToCopy.getPrevMods();
        matricId = studentToCopy.getMatricId();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code MatricId} of the {@code Student} that we are building.
     */
    public StudentBuilder withMatricId(String matricId) {
        this.matricId = new MatricId(matricId);
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code Student} that we are building.
     */
    public StudentBuilder withCredits(String credits) {
        this.credits = new Credits(credits);
        return this;
    }

    /**
     * Sets the {@code PrevMods} of the {@code Student} that we are building.
     */
    public StudentBuilder withPrevMods(String prevMods) {
        this.prevMods = new PrevMods(prevMods);
        return this;
    }

    public Student build() {
        return new Student(name, credits, prevMods, matricId, tags);
    }

}