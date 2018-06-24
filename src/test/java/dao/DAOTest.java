package dao;

import dao.DAO;
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
import javax.inject.Named;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * JUnit 4, weil es Arquillian besser unterst&uuml;zt.
 */
@RunWith(Arquillian.class)
public class DAOTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "DAOTest.war")
                .addPackage(DAO.class.getPackage())
                .addPackage(Nutzer.class.getPackage())
                .addPackage(Bezirk.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    private DAO dao;

    private static Nutzer arne;

    @Before
    public void setUp() throws Exception {
        arne = new Nutzer(1);
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
        dao.merge(arne);
    }

    @After
    public void tearDown() throws Exception {
        dao.deleteSuchanfrageByNutzer(arne);
        dao.deleteMatchanfrageByNutzer(arne);
        dao.deleteNutzer(arne);
    }

    @Test
    public void findBezirkByNameTest() {
        Bezirk actualBezirk     = dao.findBezirkByName("Mitte");
        Bezirk expectedBezirk   =  new Bezirk(1,"Mitte");

        assertEquals(expectedBezirk, actualBezirk );
    }

    @Test
    public void findBezirkListTest(){
        ArrayList<Bezirk> actualBezirkList  = dao.findBezirkList();
        Bezirk expectedBezirk               = new Bezirk(3, "Pankow");

        assertEquals(expectedBezirk, actualBezirkList.get(2));
    }

    @Test
    public void findFreizeitaktivitaetenByIDTest() {
        Freizeitaktivitaeten actualFreizeitaktivitaet   = dao.findFreizeitaktivitaetenByID(4);
        String expectedFreizeitaktivitaetenName         = "Camping";

        assertEquals(expectedFreizeitaktivitaetenName, actualFreizeitaktivitaet.getNameAktivitaet());
    }

    @Test
    public void findFreizeitaktivitaetenByNameTest() {
        Freizeitaktivitaeten actualFreizeitaktivitaet   = dao.findFreizeitaktivitaetenByName("Segeln");
        int expectedFreizeitaktivitaetenID              = 21;

        assertEquals(expectedFreizeitaktivitaetenID, actualFreizeitaktivitaet.getId());
    }

    @Test
    public void findFreizeitaktivitaetenListTest() {
        ArrayList<Freizeitaktivitaeten> actualFreizeitaktivitaetenList  = dao.findFreizeitaktivitaetenList();
        Freizeitaktivitaeten expectedFreizeitaktivitaet                 = new Freizeitaktivitaeten(15, "Kochen");

        assertEquals(expectedFreizeitaktivitaet, actualFreizeitaktivitaetenList.get(14));
    }

    @Test
    public void findMatchanfragenByNutzerIDList() {

    }
}
