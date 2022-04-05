package repository;

import domain.Assignment;
import validation.*;

import java.io.*;
import java.util.stream.Collectors;

public class AssignmentFileRepository extends AbstractFileRepository<String, Assignment> {

    public AssignmentFileRepository(Validator<Assignment> validator, String filename) {
        super(validator, filename);
        loadFromFile();
    }

    protected void loadFromFile() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            buffer.lines().collect(Collectors.toList()).forEach(line -> {
                String[] result = line.split("#");
                Assignment assignment = new Assignment(result[0], result[1], Integer.parseInt(result[2]), Integer.parseInt(result[3]));
                try {
                    super.save(assignment);
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void writeToFile(Assignment assignment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(assignment.getId() + "#" + assignment.getDescription() + "#" + assignment.getDeadline() + "#" + assignment.getStartWeek() + "\n");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void writeAllToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            super.entities.values().forEach(tema -> {
                try {
                    bw.write(tema.getId() + "#" + tema.getDescription() + "#" + tema.getDeadline() + "#" + tema.getStartWeek() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
