package service;

import domain.Assignment;
import domain.Grade;
import domain.Pair;
import domain.Student;
import repository.AssignmentXmlRepository;
import repository.GradeXmlRepository;
import repository.StudentXmlRepository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private final StudentXmlRepository studentXmlRepository;
    private final AssignmentXmlRepository assignmentXmlRepository;
    private final GradeXmlRepository gradeXmlRepository;

    public Service(StudentXmlRepository studentXmlRepository,
                   AssignmentXmlRepository assignmentXmlRepository,
                   GradeXmlRepository gradeXmlRepository) {
        this.studentXmlRepository = studentXmlRepository;
        this.assignmentXmlRepository = assignmentXmlRepository;
        this.gradeXmlRepository = gradeXmlRepository;
    }

    public Iterable<Student> findAllStudents() { return studentXmlRepository.findAll(); }

    public Iterable<Assignment> findAllAssignments() { return assignmentXmlRepository.findAll(); }

    public Iterable<Grade> findAllGrades() { return gradeXmlRepository.findAll(); }

    public int saveStudent(String id, String name, int group) {
        Student student = new Student(id, name, group);
        Student result = studentXmlRepository.save(student);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveAssignment(String id, String description, int deadline, int startWeek) {
        Assignment assignment = new Assignment(id, description, deadline, startWeek);
        Assignment result = assignmentXmlRepository.save(assignment);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveGrade(String studentId, String assignmentId, double grade, int deliveryWeek, String feedback) {
        if (studentXmlRepository.findOne(studentId) == null || assignmentXmlRepository.findOne(assignmentId) == null) {
            return -1;
        }
        int deadline = assignmentXmlRepository.findOne(assignmentId).getDeadline();

        if (deliveryWeek - deadline > 2) {
            grade = 1;
        } else {
            grade = grade - 2.5 * (deliveryWeek - deadline);
        }
        Grade nota = new Grade(new Pair<>(studentId, assignmentId), grade, deliveryWeek, feedback);
        Grade result = gradeXmlRepository.save(nota);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int deleteStudent(String id) {
        Student result = studentXmlRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteAssignment(String id) {
        Assignment result = assignmentXmlRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String newName, int newGroup) {
        Student newStudent = new Student(id, newName, newGroup);
        Student result = studentXmlRepository.update(newStudent);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateAssignment(String id, String newDescription, int newDeadline, int newStartWeek) {
        Assignment assignment = new Assignment(id, newDescription, newDeadline, newStartWeek);
        Assignment result = assignmentXmlRepository.update(assignment);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int numberOfWeeks) {
        Assignment assignment = assignmentXmlRepository.findOne(id);

        if (assignment != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= assignment.getDeadline()) {
                int deadlineNou = assignment.getDeadline() + numberOfWeeks;
                return updateAssignment(assignment.getId(), assignment.getDescription(), deadlineNou, assignment.getStartWeek());
            }
        }
        return 0;
    }

    public void createStudentFile(String studentId, String assignmentId) {
        Grade nota = gradeXmlRepository.findOne(new Pair<>(studentId, assignmentId));

        gradeXmlRepository.createFile(nota);
    }
}
