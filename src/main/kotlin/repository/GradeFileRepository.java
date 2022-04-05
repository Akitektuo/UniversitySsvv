package repository;

import domain.Grade;
import domain.Pair;
import validation.ValidationException;
import validation.Validator;

import java.io.*;
import java.util.stream.Collectors;

public class GradeFileRepository extends AbstractFileRepository<Pair<String, String>, Grade> {

    public GradeFileRepository(Validator<Grade> validator, String fileName) {
        super(validator, fileName);
        loadFromFile();
    }

    protected void loadFromFile() {
        try (var buffer = new BufferedReader(new FileReader(fileName))) {
            buffer.lines().collect(Collectors.toList()).forEach(line -> {
                var result = line.split("#");
                var grade = new Grade(new Pair(result[0], result[1]),
                        Double.parseDouble(result[2]),
                        Integer.parseInt(result[3]),
                        result[4]);
                try {
                    super.save(grade);
                } catch (ValidationException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeToFile(Grade grade) {
        try (var writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(grade.getId().getFirst() + "#" + grade.getId().getSecond() + "#" + grade.getGrade() + "#"
                    + grade.getWeekDeadline() + "#" + grade.getFeedback() + "\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            entities.values().forEach(grade -> {
                try {
                    writer.write(grade.getId().getFirst() + "#" + grade.getId().getSecond() + "#" + grade.getGrade()
                            + "#" + grade.getWeekDeadline() + "#" + grade.getFeedback() + "\n");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

//    protected void createFile(Nota notaObj) {
//        String idStudent = notaObj.getId().getObject1();
//        StudentValidator sval = new StudentValidator();
//        TemaValidator tval = new TemaValidator();
//        StudentXMLRepository srepo = new StudentXMLRepository(sval, "studenti.txt");
//        TemaXMLRepository trepo = new TemaXMLRepository(tval, "teme.txt");
//
//        Student student = srepo.findOne(idStudent);
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getNume() + ".txt", false))) {
//            super.findAll().forEach(nota -> {
//                if (nota.getId().getObject1().equals(idStudent)) {
//                    try {
//                        bw.write("Tema: " + nota.getId().getObject2() + "\n");
//                        bw.write("Nota: " + nota.getNota() + "\n");
//                        bw.write("Predata in saptamana: " + nota.getSaptamanaPredare() + "\n");
//                        bw.write("Deadline: " + trepo.findOne(nota.getId().getObject2()).getDeadline() + "\n");
//                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
}