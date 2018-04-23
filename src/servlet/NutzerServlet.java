package servlet;

import ejb.NutzerService;
import models.Geschlecht;
import models.Nutzer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

@WebServlet(name = "NutzerServlet", urlPatterns = {"/NutzerServlet"})
public class NutzerServlet extends HttpServlet {

    @EJB
    private NutzerService nutzerService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Nutzer nutzer = new Nutzer();

        // Geburtstadatum hinzufügen
        Calendar calender = Calendar.getInstance();
        calender.set(1975,Calendar.MARCH, 15);
        Date geburtstdatum = new Date(calender.getTime().getTime());

        nutzer.setMail("pippi@langstrumpf.de");
        nutzer.setGeburtsdatum(geburtstdatum);
        nutzer.setVorname("Pippi");
        nutzer.setNachname("Langstrumpf");
        nutzer.setPasswort("pippihatkeineAhnung");
        nutzer.setGeschlecht(Geschlecht.WEIBLICH);

        nutzerService.persist(nutzer);
    }
}
