package console;

import service.Service;

import java.util.Scanner;

public class UI {
    private final Service service;

    public UI(Service service) {
        this.service = service;
    }

    public void printMenu() {
        System.out.println("""
                11. Display all students.
                12. Display all assignments.
                13. Display all grades.
                
                21. Add new student.
                22. Add new assignment.
                23. Set student's grade for an assignment.
                
                31. Remove an existing student.
                32. Remove an existing assignment.
                
                4. Update a student.
                5. Extend assignment deadline.
                0. Exit
                """);
    }

    public void displayAllStudents() {
        for (var student : service.findAllStudents()) {
            System.out.println(student);
        }
    }

    public void displayAllAssignments() {
        for (var assignment : service.findAllAssignments()) {
            System.out.println(assignment);
        }
    }

    public void displayAllGrades() {
        for (var grades : service.findAllGrades()) {
            System.out.println(grades);
        }
    }

    public void addStudent() {
        var scanner = new Scanner(System.in);

        System.out.println("Student ID: ");
        var id = scanner.nextLine();

        System.out.println("Student name: ");
        var name = scanner.nextLine();

        System.out.println("Student group: ");
        var group = scanner.nextInt();

        if (service.saveStudent(id, name, group) != 0) {
            System.out.println("Student successfully added!\n");
        } else {
            System.out.println("Invalid or already existent student!\n");
        }
    }

    public void addAssignment() {
        var scanner = new Scanner(System.in);

        System.out.println("Assignment ID: ");
        var id = scanner.nextLine();

        System.out.println("Assignment description: ");
        var description = scanner.nextLine();

        System.out.println("Assignment deadline: ");
        var deadline = scanner.nextInt();

        System.out.println("Assignment start week: ");
        var startWeek = scanner.nextInt();

        if (service.saveAssignment(id, description, deadline, startWeek) != 0) {
            System.out.println("Assignment successfully added!\n");
        } else {
            System.out.println("Invalid or already existent assignment!\n");
        }
    }

    public void addGrade() {
        var scanner = new Scanner(System.in);

        System.out.println("Student ID: ");
        var studentId = scanner.nextLine();

        System.out.println("Assignment ID: ");
        var assignmentId = scanner.nextLine();

        System.out.println("Grade: ");
        var line = scanner.nextLine();
        var grade = Double.parseDouble(line);

        System.out.println("Assignment hand-in week: ");
        line = scanner.nextLine();
        var handInWeek = Integer.parseInt(line);

        System.out.println("Assignment feedback: ");
        var feedback = scanner.nextLine();

        var result = service.saveGrade(studentId, assignmentId, grade, handInWeek, feedback);
        if (result == 1) {
            service.createStudentFile(studentId, assignmentId);
            System.out.println("Grade successfully added!\n");
        } else if (result == 0) {
            System.out.println("Existent grade!\n");
        } else {
            System.out.println("Nonexistent student/assignment!\n");
        }
    }

    public void removeStudent() {
        var scanner = new Scanner(System.in);

        System.out.println("Student ID: ");
        var id = scanner.nextLine();

        if (service.deleteStudent(id) != 0) {
            System.out.println("Student successfully removed!\n");
        } else {
            System.out.println("Nonexistent student!\n");
        }
    }

    public void removeAssignment() {
        var scanner = new Scanner(System.in);

        System.out.println("Assignment ID: ");
        var id = scanner.nextLine();

        if (service.deleteAssignment(id) != 0) {
            System.out.println("Assignment successfully removed!\n");
        } else {
            System.out.println("Nonexistent assignment!\n");
        }
    }

    public void updateStudent() {
        var scanner = new Scanner(System.in);

        System.out.println("Student ID: ");
        var id = scanner.nextLine();

        System.out.println("New student name: ");
        var newName = scanner.nextLine();

        System.out.println("New student group: ");
        var newGroup = scanner.nextInt();

        if (service.updateStudent(id, newName, newGroup) != 0) {
            System.out.println("Student successfully updated!\n");
        } else {
            System.out.println("Nonexistent student!\n");
        }
    }

    public void extendDeadline() {
        var scanner = new Scanner(System.in);

        System.out.println("Assignment ID: ");
        var id = scanner.nextLine();

        System.out.println("Number of weeks: ");
        var numberOfWeeks = scanner.nextInt();

        if (service.extendDeadline(id, numberOfWeeks) != 0) {
            System.out.println("Deadline extended successfully!\n");
        } else {
            System.out.println("Nonexistent assignment!\n");
        }
    }

    public void run() {
        var scanner = new Scanner(System.in);
        var command = -1;

        printMenu();

        while (command != 0) {
            System.out.println("> ");
            command = scanner.nextInt();

            switch (command) {
                case 11 -> displayAllStudents();
                case 12 -> displayAllAssignments();
                case 13 -> displayAllGrades();
                case 21 -> addStudent();
                case 22 -> addAssignment();
                case 23 -> addGrade();
                case 31 -> removeStudent();
                case 32 -> removeAssignment();
                case 4 -> updateStudent();
                case 5 -> extendDeadline();
                case 0 -> command = 0;
            }
        }
    }
}
