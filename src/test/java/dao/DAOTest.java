package dao;

import models.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Die Integrationstests der Verbindung zur Datenbank. Bei den Tests wurden stichprobenartig Methoden der
 * {@code DAO}-Klasse ausgew&auml;hlt und unter erzeugen eines Setup's getestet.
 * Dieser Test musste aufgrund des Arquillian-Frameworks mit JUnit 4 ausgef&uuml;hrt werden,
 * da JUnit 5 noch keine volle Unterst&uuml;tzung f&uuml;r Arquillian bietet.
 */
@RunWith(Arquillian.class)
public class DAOTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "DAOTest.war")
                .addPackage(DAO.class.getPackage())
                .addPackage(Nutzer.class.getPackage())
                .addPackage(Bezirk.class.getPackage())
                .addPackage(Sprache.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private DAO dao;

    private Nutzer arne;
    private Nutzer joe;
    private Nutzer kalle;
    private Nutzer luise;

    private Matchanfragen matchanfragenArneJoe;
    private Matchanfragen matchanfragenArneLuise;
    private Matchanfragen matchanfragenJoeLuise;
    private Matchanfragen matchanfragenKalleLuise;
    private Matchanfragen matchanfragenLuiseArne;

    private Suchanfrage suchanfrageLuise;

    @Before
    public void setUp() throws Exception {
        arne = new Nutzer();
        arne.setMail("arne.shaker@web.de");
        arne.setVorname("Arne");
        arne.setNachname("Shaker");
        arne.setBezirk(new Bezirk(1,"Mitte"));
        arne.setPasswort("test1234");
        arne.setGeburtsdatum(new Date(1993,6,16));
        arne.setGeschlecht(Geschlecht.MAENNLICH);
        arne.addSprache(new Sprache(5,"Deutsch"));
        arne.addSprache(new Sprache(6,"Englisch"));
        arne.addSprache(new Sprache(9,"Grichisch"));
        arne.addSprache(new Sprache(17,"Polnisch"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(2, "Basketball"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(9, "Golfen"));

        joe = new Nutzer();
        joe.setMail("joe.beer@web.de");
        joe.setVorname("Joe");
        joe.setNachname("Beer");
        joe.setBezirk(new Bezirk(11,"Lichtenberg"));
        joe.setPasswort("test1234");
        joe.setGeburtsdatum(new Date(1989,12,11));
        joe.setGeschlecht(Geschlecht.MAENNLICH);
        joe.addSprache(new Sprache(1,"Bosnisch"));
        joe.addSprache(new Sprache(6,"Englisch"));
        joe.addSprache(new Sprache(10,"Irisch"));
        joe.addSprache(new Sprache(17,"Polnisch"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(3, "Bergsteigen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(5, "Filmen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(8, "Gaming"));

        kalle = new Nutzer();
        kalle.setMail("kalle.soldier@web.de");
        kalle.setVorname("Kalle");
        kalle.setNachname("Soldier");
        kalle.setBezirk(new Bezirk(2,"Friedrichshain-Kreuzberg"));
        kalle.setPasswort("test1234");
        kalle.setGeburtsdatum(new Date(1990,6,17));
        kalle.setGeschlecht(Geschlecht.MAENNLICH);
        kalle.addSprache(new Sprache(11,"Englisch"));
        kalle.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));

        luise = new Nutzer();
        luise.setMail("luise.hertha@web.de");
        luise.setVorname("Luise");
        luise.setNachname("Hertha");
        luise.setBezirk(new Bezirk(3,"Pankow"));
        luise.setPasswort("test1234");
        luise.setGeburtsdatum(new Date(1993, 3,27));
        luise.setGeschlecht(Geschlecht.WEIBLICH);
        luise.addSprache(new Sprache(5,"Bosnisch"));
        luise.addSprache(new Sprache(7,"Chinesisch"));
        luise.addSprache(new Sprache(22,"Norwegisch"));
        luise.addSprache(new Sprache(27,"Ungarisch"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(7, "Fußball"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(9, "Golfen"));

        dao.merge(arne);
        dao.merge(joe);
        dao.merge(kalle);
        dao.merge(luise);

        matchanfragenArneJoe = new Matchanfragen(new MatchId(dao.findNutzerByMail("arne.shaker@web.de").getId(),dao.findNutzerByMail("joe.beer@web.de")
                .getId(),1));
        matchanfragenArneJoe.setAngenommen((byte) 0);

        matchanfragenArneLuise = new Matchanfragen(new MatchId(dao.findNutzerByMail("arne.shaker@web.de").getId(),dao.findNutzerByMail("luise.hertha@web.de").getId(),7));
        matchanfragenArneLuise.setAngenommen((byte) 0);

        matchanfragenJoeLuise = new Matchanfragen(new MatchId(dao.findNutzerByMail("joe.beer@web.de").getId(),dao.findNutzerByMail("luise.hertha@web.de").getId(),22));
        matchanfragenJoeLuise.setAngenommen((byte) 0);

        matchanfragenKalleLuise = new Matchanfragen(new MatchId(dao.findNutzerByMail("kalle.soldier@web.de").getId(),dao.findNutzerByMail("luise.hertha@web.de").getId(),27));
        matchanfragenKalleLuise.setAngenommen((byte) 0);

        matchanfragenLuiseArne = new Matchanfragen(new MatchId(dao.findNutzerByMail("luise.hertha@web.de").getId(),dao.findNutzerByMail("arne.shaker@web.de").getId(),5));
        matchanfragenLuiseArne.setAngenommen((byte) 0);

        dao.merge(matchanfragenArneJoe);
        dao.merge(matchanfragenArneLuise);
        dao.merge(matchanfragenJoeLuise);
        dao.merge(matchanfragenKalleLuise);
        dao.merge(matchanfragenLuiseArne);

        suchanfrageLuise = new Suchanfrage(11,(byte)15,(byte)35,Geschlecht.MAENNLICH, dao.findNutzerByMail("luise.hertha@web.de"));

        dao.merge(suchanfrageLuise);
    }

    @After
    public void tearDown() throws Exception {
        Nutzer nutzerToDelete1 = dao.findNutzerByMail("arne.shaker@web.de");
        dao.deleteSuchanfrageByNutzer(nutzerToDelete1);
        dao.deleteMatchanfrageByNutzer(nutzerToDelete1);
        dao.deleteNutzer(nutzerToDelete1);

        Nutzer nutzerToDelete2 = dao.findNutzerByMail("joe.beer@web.de");
        dao.deleteSuchanfrageByNutzer(nutzerToDelete2);
        dao.deleteMatchanfrageByNutzer(nutzerToDelete2);
        dao.deleteNutzer(nutzerToDelete2);

        Nutzer nutzerToDelete3 = dao.findNutzerByMail("kalle.soldier@web.de");
        dao.deleteSuchanfrageByNutzer(nutzerToDelete3);
        dao.deleteMatchanfrageByNutzer(nutzerToDelete3);
        dao.deleteNutzer(nutzerToDelete3);

        Nutzer nutzerToDelete4 = dao.findNutzerByMail("luise.hertha@web.de");
        dao.deleteSuchanfrageByNutzer(nutzerToDelete4);
        dao.deleteMatchanfrageByNutzer(nutzerToDelete4);
        dao.deleteNutzer(nutzerToDelete4);
    }

    @Test
    public void findBezirkByName_Test() {
        Bezirk actualBezirk     = dao.findBezirkByName("Mitte");
        Bezirk expectedBezirk   =  new Bezirk(1,"Mitte");

        assertEquals(expectedBezirk, actualBezirk );

    }

    @Test
    public void findBezirkList_Test(){
        ArrayList<Bezirk> actualBezirkList  = dao.findBezirkList();
        Bezirk expectedBezirk               = new Bezirk(3, "Pankow");

        assertEquals(expectedBezirk, actualBezirkList.get(2));
    }

    @Test
    public void findFreizeitaktivitaetenByID_Name_Test() {
        Freizeitaktivitaeten actualFreizeitaktivitaet   = dao.findFreizeitaktivitaetenByID(4);
        Freizeitaktivitaeten expectedFreizeitaktivitaet = dao.findFreizeitaktivitaetenByID(4);

        assertEquals(expectedFreizeitaktivitaet, actualFreizeitaktivitaet);
    }

    @Test
    public void findFreizeitaktivitaetenList_Test() {
        ArrayList<Freizeitaktivitaeten> actualFreizeitaktivitaetenList  = dao.findFreizeitaktivitaetenList();
        Freizeitaktivitaeten expectedFreizeitaktivitaet1                 = new Freizeitaktivitaeten(15, "Kochen");
        Freizeitaktivitaeten expectedFreizeitaktivitaet2                 = new Freizeitaktivitaeten(17, "Literatur");
        Freizeitaktivitaeten expectedFreizeitaktivitaet3                 = new Freizeitaktivitaeten(23, "Snowboarden");
        Freizeitaktivitaeten expectedFreizeitaktivitaet4                 = new Freizeitaktivitaeten(25, "Tanzen");

        assertEquals(expectedFreizeitaktivitaet1, actualFreizeitaktivitaetenList.get(14));
        assertEquals(expectedFreizeitaktivitaet2, actualFreizeitaktivitaetenList.get(16));
        assertEquals(expectedFreizeitaktivitaet3, actualFreizeitaktivitaetenList.get(22));
        assertEquals(expectedFreizeitaktivitaet4, actualFreizeitaktivitaetenList.get(24));
    }

    @Test
    public void findMatchanfragenByNutzerIDList_Test() {
        ArrayList<Matchanfragen> actualMatchanfragenListLuise    = dao.findMatchanfragenByNutzerIDList(dao.findNutzerByMail("luise.hertha@web.de").getId());
        ArrayList<Nutzer> expectedMatchanfragenListLuise         = new ArrayList<>();

        expectedMatchanfragenListLuise.add(dao.findNutzerByMail("arne.shaker@web.de"));
        expectedMatchanfragenListLuise.add(dao.findNutzerByMail("joe.beer@web.de"));
        expectedMatchanfragenListLuise.add(dao.findNutzerByMail("kalle.soldier@web.de"));

        assertEquals(expectedMatchanfragenListLuise.get(0).getId(), actualMatchanfragenListLuise.get(0).getId().getInitiator());
        assertEquals(expectedMatchanfragenListLuise.get(1).getId(), actualMatchanfragenListLuise.get(1).getId().getInitiator());
        assertEquals(expectedMatchanfragenListLuise.get(2).getId(), actualMatchanfragenListLuise.get(2).getId().getInitiator());
    }

    @Test
    public void findSpracheByID_Name_Test() {
        Sprache actualSprache   = dao.findSpracheByID(17);
        Sprache expectedSprache = dao.findSpracheByName("Polnisch");

        assertEquals(expectedSprache, actualSprache);
    }

    @Test
    public void findSuchanfrage_Test() {
        Suchanfrage actualSuchanfrage = dao.findSuchanfrage(11, dao.findNutzerByMail("luise.hertha@web.de"));

        assertEquals(Geschlecht.MAENNLICH, actualSuchanfrage.getParamGeschlecht());
        assertEquals((byte) 15, actualSuchanfrage.getParamAlterMin());
        assertEquals((byte) 35, actualSuchanfrage.getParamAlterMax());
    }
}
