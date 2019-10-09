package mams.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.student.*;
import mams.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Mams} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new MatID("A0180001B"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new MatID("A0180002C"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new MatID("A01700003D"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new Phone("91031282"), new MatID("A0142200E"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new MatID("A01239123F"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new MatID("A0181243R"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyMams getSampleMams() {
        Mams sampleAb = new Mams();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
