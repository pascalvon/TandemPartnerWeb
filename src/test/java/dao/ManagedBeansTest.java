package dao;

import jsfbeans.HomeManagedBean;
import models.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Date;


class ManagedBeansTest {


    private static Nutzer arne;
    private static Nutzer joe;
    private static Nutzer kalle;
    private static Nutzer luis;
    private static DAO dao;

    @BeforeAll
    static void buildEnv() {

        joe = new Nutzer();
        joe.setMail("unit.test@web.de");
        joe.setVorname("Unit");
        joe.setNachname("Test");
        joe.setBezirk(new Bezirk(11,"Lichtenberg"));
        joe.setPasswort("test1234");
        joe.setGeburtsdatum(new Date(1989,12,11));
        joe.setGeschlecht(Geschlecht.MAENNLICH);

        joe.addSprache(new Sprache(4,"Bengalisch"));
        joe.addSprache(new Sprache(10,"Englisch"));
        joe.addSprache(new Sprache(14,"Georgisch"));
        joe.addSprache(new Sprache(23,"Polnisch"));

        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(3, "Bergsteigen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(5, "Filmen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(8, "Gaming"));

    }

    @Test
    void calculateMatchanfragenTest ()  {
      dao = Mockito.mock(DAO.class);

        HomeManagedBean mb = new HomeManagedBean(dao, joe);
        mb.getMatchanfragenModelArrayList();
        Mockito.when(dao.findNutzerByID(0)).thenReturn(joe);

    }
}