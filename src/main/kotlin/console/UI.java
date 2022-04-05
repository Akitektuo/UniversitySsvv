package console;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import service.Service;

import java.util.Scanner;

public class UI {
    private final Service service;

    public UI(Service service) {
        this.service = service;
    }

    public void printMenu() {
        System.out.println(
                "11. Display all students.\n" +
                        "12. Display all assignments.\n" +
                        "13. Display all grades.\n\n" +

                        "21. Add new student.\n" +
                        "22. Add new assignment.\n" +
                        "23. Set student's grade for an assignment.\n\n" +

                        "31. Remove an existing student.\n" +
                        "32. Remove an existing assignment.\n\n" +

                        "4. Update a student.\n" +
                        "5. Extend assignment deadline.\n" +
                        "0. Exit\n");
    }

    public void displayAllStudents() {
        for (Student student : service.findAllStudents()) {
            System.out.println(student);
        }
    }

    public void displayAllAssignments() {
        for (Assignment assignment : service.findAllAssignments()) {
            System.out.println(assignment);
        }
    }

    public void displayAllGrades() {
        for (Grade grades : service.findAllGrades()) {
            System.out.println(grades);
        }
    }

    public void addStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student ID: ");
        String id = scanner.nextLine();

        System.out.print("Student name: ");
        String name = scanner.nextLine();

        System.out.print("Student group: ");
        int group = scanner.nextInt();

        if (service.saveStudent(id, name, group) != 0) {
            System.out.println("Student successfully added!\n");
        } else {
            System.out.println("Invalid or already existent student!\n");
        }
    }

    public void addAssignment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Assignment ID: ");
        String id = scanner.nextLine();

        System.out.print("Assignment description: ");
        String description = scanner.nextLine();

        System.out.print("Assignment deadline: ");
        int deadline = scanner.nextInt();

        System.out.print("Assignment start week: ");
        int startWeek = scanner.nextInt();

        if (service.saveAssignment(id, description, deadline, startWeek) != 0) {
            System.out.println("Assignment successfully added!\n");
        } else {
            System.out.println("Invalid or already existent assignment!\n");
        }
    }

    public void addGrade() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Assignment ID: ");
        String assignmentId = scanner.nextLine();

        System.out.print("Grade: ");
        String line = scanner.nextLine();
        double grade = Double.parseDouble(line);

        System.out.print("Assignment hand-in week: ");
        line = scanner.nextLine();
        int handInWeek = Integer.parseInt(line);

        System.out.print("Assignment feedback: ");
        String feedback = scanner.nextLine();

        int result = service.saveGrade(studentId, assignmentId, grade, handInWeek, feedback);
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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student ID: ");
        String id = scanner.nextLine();

        if (service.deleteStudent(id) != 0) {
            System.out.println("Student successfully removed!\n");
        } else {
            System.out.println("Nonexistent student!\n");
        }
    }

    public void removeAssignment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Assignment ID: ");
        String id = scanner.nextLine();

        if (service.deleteAssignment(id) != 0) {
            System.out.println("Assignment successfully removed!\n");
        } else {
            System.out.println("Nonexistent assignment!\n");
        }
    }

    public void updateStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student ID: ");
        String id = scanner.nextLine();

        System.out.print("New student name: ");
        String newName = scanner.nextLine();

        System.out.print("New student group: ");
        int newGroup = scanner.nextInt();

        if (service.updateStudent(id, newName, newGroup) != 0) {
            System.out.println("Student successfully updated!\n");
        } else {
            System.out.println("Nonexistent student!\n");
        }
    }

    public void extendDeadline() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Assignment ID: ");
        String id = scanner.nextLine();

        System.out.print("Number of weeks: ");
        int numberOfWeeks = scanner.nextInt();

        if (service.extendDeadline(id, numberOfWeeks) != 0) {
            System.out.println("Deadline extended successfully!\n");
        } else {
            System.out.println("Nonexistent assignment!\n");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int command = -1;

        printMenu();

        while (command != 0) {
            System.out.print("> ");
            command = scanner.nextInt();

            switch (command) {
                case 11:
                    displayAllStudents();
                    break;
                case 12:
                    displayAllAssignments();
                    break;
                case 13:
                    displayAllGrades();
                    break;
                case 21:
                    addStudent();
                    break;
                case 22:
                    addAssignment();
                    break;
                case 23:
                    addGrade();
                    break;
                case 31:
                    removeStudent();
                    break;
                case 32:
                    removeAssignment();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    extendDeadline();
                    break;
                case 0:
                    return;
            }
        }
    }
}
