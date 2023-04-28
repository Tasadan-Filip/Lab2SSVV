import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Iterator;

import static junit.framework.TestCase.assertTrue;

public class JUnitAssignmentsTest {
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
    }

    @After
    public void clear() {
        Iterable<Tema> teme = service.getAllTeme();
        Iterator<Tema> iterator = teme.iterator();
        while (iterator.hasNext()) {
            Tema tema = iterator.next();
            iterator.remove();
            service.deleteTema(tema.getID());
        }
    }

    @Test(expected = ValidationException.class)
    public void AddAssignmentWBT1() {
        Tema tema = new Tema(null, "descriere", 2, 2);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void AddAssignmentWBT2() {
        Tema tema = new Tema("abc", "", 2, 2);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void AddAssignmentWBT3() {
        Tema tema = new Tema("abc", "descriere", 15, 2);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void AddAssignmentWBT4() {
        Tema tema = new Tema("abc", "descriere", 2, -2);
        service.addTema(tema);
    }

    @Test()
    public void AddAssignmentWBT5() {
        Tema tema = new Tema("abc", "descriere", 2, 2);
        service.addTema(tema);
    }
}
