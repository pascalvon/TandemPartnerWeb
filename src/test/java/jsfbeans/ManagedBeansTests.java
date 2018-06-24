package jsfbeans;

import dao.DAO;
import models.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

/**
 * Die Testklasse f&uuml;r die ManagedBeans, welche Methoden testet,
 * die Logik enthalten.
 */
class ManagedBeansTests {


    private static DAO dao;

    private static Nutzer arne;
    private static Nutzer joe;
    private static Nutzer kalle;
    private static Nutzer luise;

    private static Matchanfragen matchanfragenArneJoe;
    private static Matchanfragen matchanfragenArneLuis;
    private static Matchanfragen matchanfragenJoeLuis;
    private static Matchanfragen matchanfragenKalleLuis;
    private static Matchanfragen matchanfragenLuisArne;

    private static ArrayList<Matchanfragen> matchanfragenListJoe = new ArrayList<>();
    private static MatchanfragenModel matchanfragenModelJoe;

    private static ArrayList<Matchanfragen> matchanfragenListArne = new ArrayList<>();
    private static MatchanfragenModel matchanfragenModelArne;



    @BeforeAll
    static void setUp() {

        // Test-Nutzer
        arne = new Nutzer(1);
        arne.setMail("arne.shaker@web.de");
        arne.setVorname("Arne");
        arne.setNachname("Shaker");
        arne.setBezirk(new Bezirk(1,"Mitte"));
        arne.setPasswort("test1234");
        arne.setGeburtsdatum(new Date(1993,6,16));
        arne.setGeschlecht(Geschlecht.MAENNLICH);
        arne.addSprache(new Sprache(9,"Deutsch"));
        arne.addSprache(new Sprache(10,"Englisch"));
        arne.addSprache(new Sprache(15,"Grichisch"));
        arne.addSprache(new Sprache(23,"Polnisch"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(2, "Basketball"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        arne.addFreizeitaktivitaeten(new Freizeitaktivitaeten(9, "Golfen"));

        joe = new Nutzer(2);
        joe.setMail("joe.beer@web.de");
        joe.setVorname("Joe");
        joe.setNachname("Beer");
        joe.setBezirk(new Bezirk(11,"Lichtenberg"));
        joe.setPasswort("test1234");
        joe.setGeburtsdatum(new Date(1989,12,11));
        joe.setGeschlecht(Geschlecht.MAENNLICH);
        joe.addSprache(new Sprache(4,"Bengalisch"));
        joe.addSprache(new Sprache(10,"Englisch"));
        joe.addSprache(new Sprache(14,"Georgisch"));
        joe.addSprache(new Sprache(23,"Polnisch"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(3, "Bergsteigen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(5, "Filmen"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        joe.addFreizeitaktivitaeten(new Freizeitaktivitaeten(8, "Gaming"));

        kalle = new Nutzer(3);
        kalle.setMail("kalle.soldier@web.de");
        kalle.setVorname("Kalle");
        kalle.setNachname("Soldier");
        kalle.setBezirk(new Bezirk(2,"Friedrichshain-Kreuzberg"));
        kalle.setPasswort("test1234");
        kalle.setGeburtsdatum(new Date(1990,6,17));
        kalle.setGeschlecht(Geschlecht.MAENNLICH);
        kalle.addSprache(new Sprache(11,"Englisch"));
        kalle.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));

        luise = new Nutzer(4);
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
        luise.addSprache(new Sprache(30,"Ungarisch"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(1, "Angeln"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(6, "Fitness"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(7, "Fußball"));
        luise.addFreizeitaktivitaeten(new Freizeitaktivitaeten(9, "Golfen"));

        // Test-Matchanfragen
        matchanfragenArneJoe = new Matchanfragen(new MatchId(1,2,4));
        matchanfragenArneJoe.setAngenommen((byte) 0);

        matchanfragenArneLuis = new Matchanfragen(new MatchId(1,4,7));
        matchanfragenArneLuis.setAngenommen((byte) 0);

        matchanfragenJoeLuis = new Matchanfragen(new MatchId(2,4,22));
        matchanfragenJoeLuis.setAngenommen((byte) 0);

        matchanfragenKalleLuis = new Matchanfragen(new MatchId(3,4,30));
        matchanfragenKalleLuis.setAngenommen((byte) 0);

        matchanfragenLuisArne = new Matchanfragen(new MatchId(4,1,9));
        matchanfragenLuisArne.setAngenommen((byte) 0);

        matchanfragenModelJoe = new MatchanfragenModel(arne,1,"Fitness", matchanfragenArneJoe);
        matchanfragenListJoe.add(matchanfragenArneJoe);

        matchanfragenModelArne = new MatchanfragenModel(luise, 3, "Angeln, Fitness, Golfen", matchanfragenLuisArne, "Empfangen" );
        matchanfragenListArne.add(matchanfragenLuisArne);


    }

    @Nested
    class HommeManagedBeanTest {

        @Test
        @DisplayName("Calculation of unanswered Matchanfragen")
        void calculateMatchanfragenTest()  {
            dao = Mockito.mock(DAO.class);
            HomeManagedBean homeJoe = new HomeManagedBean(dao, joe);

            Mockito.when(dao.findMatchanfragenByNutzerID(2)).thenReturn(matchanfragenListJoe);
            Mockito.when(dao.findNutzerByID(1)).thenReturn(arne);

            assertEquals(joe, homeJoe.getNutzer());
            assertEquals(matchanfragenModelJoe, homeJoe.getMatchanfragenModelArrayList().get(0));
        }
    }

    @Nested
    class MatchesManagedBeanTest {

        @Test
        @DisplayName("Calculation of answered Matchanfragen")
        void calculateMatchanfragenTest() {
            dao = Mockito.mock(DAO.class);
            MatchesManagedBean matchesArne = new MatchesManagedBean(dao, arne);

            Mockito.when(dao.findMatchanfragenByAllColumns(arne.getId())).thenReturn(matchanfragenListArne);
            Mockito.when(dao.findNutzerByID(4)).thenReturn(luise);

            assertEquals(arne, matchesArne.getNutzer());
            assertEquals(matchanfragenModelArne, matchesArne.getMatchanfragenModelArrayList().get(0));
        }
    }

    @Nested
    class ProfilManagedBeanTest {

        @Test
        @DisplayName("Update userprofile")
        void updateTest() {
            dao = Mockito.mock(DAO.class);
            ProfilManagedBean profilKalle = new ProfilManagedBean(dao, kalle);

            HashSet<Sprache> sprachenSetKalleNew = new HashSet<>();
            sprachenSetKalleNew.add(new Sprache(18,"Hawaiisch"));

            HashSet<Freizeitaktivitaeten> freizeitaktivitaetenSetKalleNew = new HashSet<>();
            freizeitaktivitaetenSetKalleNew.add(new Freizeitaktivitaeten(1,"Angeln"));

            assertEquals("11", profilKalle.getSelectedSprachenString());
            assertEquals("6", profilKalle.getSelectedFreizeitaktivitaetenString());

            profilKalle.setMail("kalle.student@web.de");
            profilKalle.setPassword("kalle1234");
            profilKalle.setBezirkID(2);
            profilKalle.setSelectedSprachenString("18");
            profilKalle.setSelectedFreizeitaktivitaetenString("1");

            Mockito.when(dao.findBezirkByID(2)).thenReturn(kalle.getBezirk());
            Mockito.when(dao.findSpracheByID(18)).thenReturn(new Sprache(18,"Hawaiisch"));
            Mockito.when(dao.findFreizeitaktivitaetenByID(1)).thenReturn(new Freizeitaktivitaeten(1,"Angeln"));


            assertEquals("home?faces-redirect=true", profilKalle.update());
            assertEquals(sprachenSetKalleNew, profilKalle.getNutzer().getSprachenSet());
            assertEquals(freizeitaktivitaetenSetKalleNew, profilKalle.getNutzer().getFreizeitaktivitaetenSet());
            assertEquals("kalle.student@web.de", profilKalle.getNutzer().getMail());
            assertEquals(kalle.getBezirk(), profilKalle.getNutzer().getBezirk());
            assertEquals("kalle1234", profilKalle.getPassword());
        }
    }

    @Nested
    class RegistrierenManagedBeanTest {

        @Test
        @DisplayName("Nutzer registration")
        void registerTest() {
            dao = Mockito.mock(DAO.class);
            RegistrierenManagedBean registrierenKalle = new RegistrierenManagedBean(dao, 3);

            registrierenKalle.getNutzer().setMail("kalle.soldier@web.de");
            registrierenKalle.getNutzer().setVorname("Kalle");
            registrierenKalle.getNutzer().setNachname("Soldier");
            registrierenKalle.getNutzer().setPasswort("test1234");
            registrierenKalle.getNutzer().setGeburtsdatum(new Date(1990,6,17));
            registrierenKalle.getNutzer().setGeschlecht(Geschlecht.MAENNLICH);
            registrierenKalle.setBezirkID(2);
            registrierenKalle.setSelectedSprachenString("11");
            registrierenKalle.setSelectedFreizeitaktivitaetenString("6");

            Mockito.when(dao.findBezirkByID(2)).thenReturn(new Bezirk(2,"Friedrichshain-Kreuzberg"));
            Mockito.when(dao.findSpracheByID(11)).thenReturn(new Sprache(11, "Englisch"));
            Mockito.when(dao.findFreizeitaktivitaetenByID(6)).thenReturn(new Freizeitaktivitaeten(6, "Fitness"));

            assertEquals("/nutzer/home?faces-redirect=true", registrierenKalle.register());
            assertEquals(kalle.getMail(), registrierenKalle.getNutzer().getMail());
            assertEquals(kalle.getVorname(), registrierenKalle.getNutzer().getVorname());
            assertEquals(kalle.getNachname(), registrierenKalle.getNutzer().getNachname());
            assertEquals(kalle.getPasswort(), registrierenKalle.getNutzer().getPasswort());
            assertEquals(kalle.getGeburtsdatum(), registrierenKalle.getNutzer().getGeburtsdatum());
            assertEquals(kalle.getGeschlecht(), registrierenKalle.getNutzer().getGeschlecht());
            assertEquals(kalle.getBezirk(), registrierenKalle.getNutzer().getBezirk());
            assertEquals(kalle.getSprachenSet(), registrierenKalle.getNutzer().getSprachenSet());
            assertEquals(kalle.getFreizeitaktivitaetenSet(), registrierenKalle.getNutzer().getFreizeitaktivitaetenSet());



        }
    }

}