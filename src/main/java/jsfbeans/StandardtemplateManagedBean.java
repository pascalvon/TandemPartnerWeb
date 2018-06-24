package jsfbeans;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Die {@code StandardtemplateManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r
 * die {@code standardtemplate.xhtml}.
 */
@ManagedBean
@ViewScoped
public class StandardtemplateManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO dao;

    /**
     * Das {@code Nutzer}-Objekt, welches den angemeldeten Nutzer darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Nutzer nutzer;

    /**
     * {@code String}, welcher seinen Wert &auml;ndert, je nachdem ob ein angemeldeter Nutzer existiert.
     */
    private String logStatus;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code StandardtemplateManagedBean}-Objekt und ruft dabei die Methode
     * {@link #initNutzer() initNutzer} auf.
     */
    public StandardtemplateManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Pr&uuml;ft, ob ein angemeldeter Nutzer existiert. Wenn ja wird dieser zur {@code home.xhtml} weitergeleitet
     * und f&uuml;r den Fall, dass kein angemeldeter Nutzer existiert, wird der Nutzer zur {@code login.xhtml}
     * weitergeleitet.
     *
     * @return  Je nachdem, welchen Wert {@link #nutzer nutzer} enth&auml;lt, wird die URL
     *          zur {@code home.xhtml} oder {@code login.xhtml} wiedergegeben.
     */
    public String redirectToHome() {
        if (ifNutzerIsInvalid(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "home?faces-redirect=true";
        }
    }

    /**
     * Pr&uuml;ft, ob ein angemeldeter Nutzer existiert. Wenn ja wird dieser zur {@code profil.xhtml} weitergeleitet
     * und f&uuml;r den Fall, dass kein angemeldeter Nutzer existiert, wird der Nutzer zur {@code login.xhtml}
     * weitergeleitet.
     *
     * @return Je nachdem, welchen Wert {@link #nutzer nutzer} enth&auml;lt, wird die URL
     * zur {@code profil.xhtml} oder {@code login.xhtml} wiedergegeben.
     */
    public String redirectToProfil() {
        if (ifNutzerIsInvalid(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "profil?faces-redirect=true";
        }
    }

    /**
     * Pr&uuml;ft, ob ein angemeldeter Nutzer existiert. Wenn ja wird dieser zur {@code suchanfrage.xhtml}
     * weitergeleitet und f&uuml;r den Fall, dass kein angemeldeter Nutzer existiert, wird der Nutzer zur
     * {@code login.xhtml} weitergeleitet.
     *
     * @return Je nachdem, welchen Wert {@link #nutzer nutzer} enth&auml;lt, wird die URL
     * zur {@code suchanfrage.xhtml} oder {@code login.xhtml} wiedergegeben.
     */
    public String redirectToSuchanfragen() {
        if (ifNutzerIsInvalid(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "suchanfrage?faces-redirect=true";
        }
    }

    /**
     * Pr&uuml;ft, ob ein angemeldeter Nutzer existiert. Wenn ja wird dieser zur {@code matches.xhtml} weitergeleitet
     * und f&uuml;r den Fall, dass kein angemeldeter Nutzer existiert, wird der Nutzer zur {@code login.xhtml}
     * weitergeleitet.
     *
     * @return Je nachdem, welchen Wert {@link #nutzer nutzer} enth&auml;lt, wird die URL
     * zur {@code matches.xhtml} oder {@code login.xhtml} wiedergegeben.
     */
    public String redirectToMatches() {
        if (ifNutzerIsInvalid(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "matches?faces-redirect=true";
        }
    }

    /**
     * Die Session wird ung&uuml;ltig gemacht und alle an sie gebundenen Objekte werden aufgehoben.
     *
     * @return Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer auf die {@code login.xhtml} weitergeleitet wird.
     */
    public String logout()  {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    /**
     * Pr&uuml;ft, ob ein angemeldeter Nutzer existiert und setzt dementsrpechend den Wert von
     * {@link #logStatus logStatus}.
     *
     * @return Gibt den Wert von {@link #logStatus logStatus} wieder.
     */
    public String getLogStatus() {
        if (ifNutzerIsInvalid(nutzer.getMail())) {
            logStatus = "Login";
        } else {
            logStatus = "Logout";
        }
        return logStatus;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der
     * {@code LoginManagedBean} solange existiert, wie die Session l&auml;uft. Anschließend wird das
     * {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen. Falls
     * das {@code Nutzer}-Objekt null ist, wird {@link #nutzer nutzer} instanziiert.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
        if (loginManagedBean.getNutzer() == null) {
            nutzer = new Nutzer();
        }
    }

    /**
     * &Uuml;berpr&uuml;ft, ob die &uuml;bergebene E-Mail-Adresse einer Nutzer-Entit&auml;t aus der Datenbank
     * geh&ouml;rt. Falls ja, gibt die Methode false zur&uuml;ck und falls eine {@code NullPointerException} fliegt,
     * gibt die Methode
     * true zur&uuml;ck.
     *
     * @param   mail Die E-Mail-Adresse, welche gepr&uuml;ft werden soll.
     * @return  Gibt false zur&uuml;ck, wenn die E-Mail-Adresse einer Nutzer-Entit&auml;t geh&ouml;rt und true, wenn
     *          eine NullPointerException fliegt.
     */
    private boolean ifNutzerIsInvalid(String mail) {
        try {
            Nutzer n = dao.findNutzerByMail(mail);
            return !n.getMail().equals(nutzer.getMail());
        } catch (NullPointerException e) {
            return true;
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
