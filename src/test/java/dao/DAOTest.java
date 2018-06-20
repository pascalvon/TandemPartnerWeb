package dao;

import models.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import javax.ejb.EJB;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DAOTest {

    static Nutzer nutzer;
    static DAO dao = new DAO();

    @BeforeAll
    static void buildEnv() {
        nutzer = new Nutzer();
        nutzer.setMail("unit.test@web.de");
        nutzer.setVorname("Unit");
        nutzer.setNachname("Test");
        nutzer.setBezirk(new Bezirk(1,"Mitte"));
        nutzer.setPasswort("test1234");
        nutzer.setGeburtsdatum(new Date(1989,12,11));
        nutzer.setGeschlecht(Geschlecht.MAENNLICH);

        nutzer.addSprache(new Sprache(1,"Albanisch"));
        nutzer.addSprache(new Sprache(2,"Arabisch"));
        nutzer.addSprache(new Sprache(3,"Armenisch"));
        nutzer.addSprache(new Sprache(4,"Bengalisch"));

        nutzer.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        nutzer.addFreizeitaktivitaeten(new Freizeitaktivitaeten(3, "Bergsteigen"));
        nutzer.addFreizeitaktivitaeten(new Freizeitaktivitaeten(5, "Filmen"));
        nutzer.addFreizeitaktivitaeten(new Freizeitaktivitaeten(8, "Gaming"));

    }

    @Test
    void findNutzerByMailTest() {
        dao.merge(nutzer);

        Nutzer resultNutzer = dao.findNutzerByMail("unit.test@web.de");
        assertTrue(resultNutzer.equals(nutzer));
    }
}