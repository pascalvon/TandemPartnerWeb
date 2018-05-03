package servlets;

import dao.NutzerDAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@ManagedBean
@SessionScoped
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    // =========================== Class Variables ===========================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer aktuellerNutzer;
    @PersistenceContext(unitName = "ExperimentalJPADatabase")
    private EntityManager em;

    // =============================  Variables  =============================79
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79
    public Nutzer login(String mail, String passwort) throws NullPointerException {
        if (validateNutzer(mail, passwort)) {
            aktuellerNutzer = getNutzer(mail);
            return aktuellerNutzer;
        }

        throw new NullPointerException();
    }

    private boolean validateNutzer(String mail, String passwort) {
        Nutzer n = find(mail);

        return n.getPasswort().equals(passwort);
    }

    private Nutzer getNutzer(String mail) {
        return find(mail);

    }

    private Nutzer find(String Mail) {
        return em.createNamedQuery("findByMail", Nutzer.class)
                .setParameter("vn", Mail)
                .getSingleResult();
    }
    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
