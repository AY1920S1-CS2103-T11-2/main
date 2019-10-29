package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;

import mams.logic.commands.exceptions.CommandException;

import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Credits;
import mams.model.student.Student;
import mams.model.tag.Tag;

import static mams.logic.commands.AddModCommand.MESSAGE_ADD_MOD_SUCCESS;
import static mams.logic.commands.AddModCommand.MESSAGE_DUPLICATE_MODULE;
import static mams.logic.commands.RemoveModCommand.MESSAGE_REMOVE_MOD_SUCCESS;
import static mams.logic.commands.RemoveModCommand.MESSAGE_MISSING_MODULE;
import static mams.logic.commands.ModCommand.MESSAGE_INVALID_MODULE;
import static mams.logic.commands.SetCredits.MESSAGE_CREDIT_CHANGE_SUCCESS;
/**
 * Approves a appeal in mams.
 */
public class ApproveCommand extends Approve {

    private final Index index;
    private final String reason;

    public ApproveCommand(Index index, String reason) {
        requireNonNull(index, reason);

        this.index = index;
        this.reason = reason;

    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Appeal> lastShownList = model.getFilteredAppealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
        }

        Appeal approvedAppeal;

        Appeal appealToApprove = lastShownList.get(index.getZeroBased());

        if (appealToApprove.isResolved() == false) {
            Student studentToEdit;
            Student editedStudent;
            Module moduleToEdit;
            Module editedModule;
            String feedback = "";
            String target = "";
            String change = "";
            String moduleCode;

            List<Student> lastShownStudentList = model.getFilteredStudentList();
            List<Module> lastShownModuleList = model.getFilteredModuleList();

            String appealType = appealToApprove.getAppealType();
            String studentToEditId = appealToApprove.getStudentId();

            if (appealType.equalsIgnoreCase("Increase workload")) {

                List<Student> studentToCheckList = lastShownStudentList.stream()
                                .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                .collect(Collectors.toList());

                if (studentToCheckList.isEmpty()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                }
                studentToEdit = studentToCheckList.get(0);

                editedStudent = new Student(studentToEdit.getName(),
                            new Credits((Integer.toString(appealToApprove.getStudentWorkload()))),
                            studentToEdit.getPrevMods(),
                            studentToEdit.getMatricId(),
                            studentToEdit.getTags());
                model.setStudent(studentToEdit, editedStudent);
                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                feedback += MESSAGE_CREDIT_CHANGE_SUCCESS;
                target += studentToEditId;

            } else if (appealType.equalsIgnoreCase("Drop module")) {
                moduleCode = appealToApprove.getModuleToDrop();

                //Check if student exists
                List<Student> studentToCheckList = lastShownStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                        .collect(Collectors.toList());
                if (studentToCheckList.isEmpty()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                }
                studentToEdit = studentToCheckList.get(0);

                //check if student has the module (ready for deletion).
                Set<Tag> studentModules = studentToEdit.getCurrentModules();
                boolean hasModule = false;
                for (Tag tag : studentModules) {
                    if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                        hasModule = true;
                    }
                }
                if (hasModule == false) {
                    throw new CommandException(MESSAGE_MISSING_MODULE);
                }

                //check if module exist
                List<Module> moduleToCheckList = lastShownModuleList.stream()
                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
                if (moduleToCheckList.isEmpty()) {
                    throw new CommandException(MESSAGE_INVALID_MODULE);
                }
                moduleToEdit = moduleToCheckList.get(0);


                Set<Tag> ret = new HashSet<>();
                Set<Tag> studentAllTags = studentToEdit.getTags();
                for (Tag tag : studentAllTags) {
                    if (!tag.getTagName().equalsIgnoreCase(moduleCode)) {
                        ret.add(tag);
                    }
                }

                //create a tag list without the student for the new module
                Set<Tag> ret2 = new HashSet<>();
                Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
                for (Tag tag : moduleAllStudents) {
                    if (!tag.getTagName().equalsIgnoreCase(studentToEdit.getMatricId().toString())) {
                        ret2.add(tag);
                    }
                }

                editedStudent = new Student(studentToEdit.getName(),
                        studentToEdit.getCredits(),
                        studentToEdit.getPrevMods(),
                        studentToEdit.getMatricId(),
                        ret);
                model.setStudent(studentToEdit, editedStudent);
                model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

                editedModule = new Module(moduleToEdit.getModuleCode(),
                        moduleToEdit.getModuleName(),
                        moduleToEdit.getModuleDescription(),
                        moduleToEdit.getLecturerName(),
                        moduleToEdit.getTimeSlot(),
                        moduleToEdit.getQuota(),
                        ret2);

                model.setModule(moduleToEdit, editedModule);
                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
                feedback = MESSAGE_REMOVE_MOD_SUCCESS;
                target = studentToEditId;
                change = editedModule.getModuleCode();

            } else {

                moduleCode = appealToApprove.getModuleToAdd();

                List<Student> studentToCheckList = lastShownStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId)).collect(Collectors.toList());
                if (studentToCheckList.isEmpty()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                }
                studentToEdit = studentToCheckList.get(0);

                //check if module exist
                List<Module> moduleToCheckList = lastShownModuleList.stream()
                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
                if (moduleToCheckList.isEmpty()) {
                    throw new CommandException(MESSAGE_INVALID_MODULE);
                }
                moduleToEdit = moduleToCheckList.get(0);


                //check if student already has module.
                Set<Tag> studentModules = studentToEdit.getCurrentModules();
                for (Tag tag : studentModules) {
                    if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                        throw new CommandException(MESSAGE_DUPLICATE_MODULE);
                    }
                }

                //add module to student.
                Set<Tag> ret = new HashSet<>();
                Set<Tag> studentAllTags = studentToEdit.getTags();
                for (Tag tag : studentAllTags) {
                    ret.add(tag);
                }
                ret.add(new Tag(moduleCode));


                //add student to module field
                Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
                Set<Tag> ret2 = new HashSet<>(moduleAllStudents);
                ret2.add(new Tag(studentToEdit.getMatricId().toString()));

                //replace old student and old module objects with edited modules.
                editedStudent = new Student(studentToEdit.getName(),
                        studentToEdit.getCredits(),
                        studentToEdit.getPrevMods(),
                        studentToEdit.getMatricId(),
                        ret);

                editedModule = new Module(moduleToEdit.getModuleCode(),
                        moduleToEdit.getModuleName(),
                        moduleToEdit.getModuleDescription(),
                        moduleToEdit.getLecturerName(),
                        moduleToEdit.getTimeSlot(),
                        moduleToEdit.getQuota(),
                        ret2);

                model.setStudent(studentToEdit, editedStudent);
                model.setModule(moduleToEdit, editedModule);
                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

                feedback = MESSAGE_ADD_MOD_SUCCESS;
                target = studentToEditId;
            }


            approvedAppeal = new Appeal(appealToApprove.getAppealId(),
                    appealToApprove.getAppealType(),
                    appealToApprove.getStudentId(),
                    appealToApprove.getAcademicYear(),
                    appealToApprove.getStudentWorkload(),
                    appealToApprove.getAppealDescription(),
                    appealToApprove.getPreviousModule(),
                    appealToApprove.getNewModule(),
                    appealToApprove.getModuleToAdd(),
                    appealToApprove.getModuleToDrop(),
                    true,
                    "APPROVED",
                    reason);
            model.setAppeal(appealToApprove, approvedAppeal);
            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            return new CommandResult(
                    generateSuccessMessage(approvedAppeal, feedback, target, change));
        } else {
            return new CommandResult(MESSAGE_APPEAL_ALREADY_APPROVED);
        }

    }

    private String generateSuccessMessage(Appeal appealToApprove, String feedback, String target, String change) {
        return "Approved " + appealToApprove.getAppealId() + "\n" + String.format(feedback, target);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApproveCommand)) {
            return false;
        }

        // state check
        ApproveCommand e = (ApproveCommand) other;
        return index.equals(e.index)
                && reason.equals(e.reason);
    }
}
