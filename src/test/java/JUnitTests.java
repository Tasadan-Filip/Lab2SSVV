import domain.Student;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class JUnitTests {
    Service service;

    @Before
    public void init() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Iterable<Student> students = service.getAllStudenti();
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            iterator.remove();
            service.deleteStudent(student.getID());
        }
    }

    @After
    public void clear() {
        Iterable<Student> students = service.getAllStudenti();
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            iterator.remove();
            service.deleteStudent(student.getID());
        }
    }

    @Test(expected = ValidationException.class)
    public void addStudentBVA1() {
        Student student = new Student("id", "nume", 109, "test@gmail.com");
        assertTrue(service.findStudent("id") == null);
        Student ret = service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentBVA2() {
        Student student = new Student("id", "nume", 110, "test@gmail.com");
        Student ret = service.addStudent(student);
    }

    @Test
    public void addStudentBVA3() {
        Student student = new Student("id", "nume", 111, "test@gmail.com");
        Student ret = service.addStudent(student);
    }

    @Test
    public void addStudentBVA4() {
        Student student = new Student("id", "nume", 937, "test@gmail.com");
        Student ret = service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentBVA5() {
        Student student = new Student("id", "nume", 938, "test@gmail.com");
        Student ret = service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentBVA6() {
        Student student = new Student("id", "nume", 939, "test@gmail.com");
        Student ret = service.addStudent(student);
    }

    @Test
    public void addStudentEC1() {
        Student student = new Student("id", "nume", 111, "test@gmail.com");
        Student ret = service.addStudent(student);
        assertTrue(ret == null);
        assertTrue(service.findStudent("id").equals(student));
    }

    @Test(expected = ValidationException.class)
    public void addStudentEC2() {
        Student student = new Student("id", "nume", 110, "test@gmail.com");
        Student ret = service.addStudent(student);
    }
}
