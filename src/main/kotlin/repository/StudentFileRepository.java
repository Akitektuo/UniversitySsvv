package repository;

import domain.Student;
import validation.*;

import java.io.*;
import java.util.stream.Collectors;

public class StudentFileRepository extends AbstractFileRepository<String, Student> {

    public StudentFileRepository(Validator<Student> validator, String fileName) {
        super(validator, fileName);
        loadFromFile();
    }

    protected void loadFromFile() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            buffer.lines().collect(Collectors.toList()).forEach(line -> {
                String[] result = line.split("#");
                Student student = new Student(result[0], result[1], Integer.parseInt(result[2]));
                try {
                    super.save(student);
                } catch (ValidationException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeToFile(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(student.getId() + "#" + student.getName() + "#" + student.getGroup() + "\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            entities.values().forEach(student -> {
                try {
                    writer.write(student.getId() + "#" + student.getName() + "#" + student.getGroup() + "\n");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
