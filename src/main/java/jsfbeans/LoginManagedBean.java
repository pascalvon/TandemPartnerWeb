package jsfbeans;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;

/**
 * Die {@code LoginManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r die {@code login.xhtml}.
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO dao;

    /**
     * Das {@code Nutzer}-Objekt, in dem der angemeldete Nutzer gehalten wird, nachdem die Methode
     * {@link #login() login} erfolgreich aufgerufen wurde.
     */
    private Nutzer nutzer;

    /**
     * {@code String}, f&uuml;r die Eingabe der E-Mail-Adresse des Nutzers.
     */
    private String mail;

    /**
     * {@code String}, f&uuml;r die Eingabe des Passworts des Nutzers.
     */
    private String password;

    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    /**
     * Holt sich &uuml;ber die {@link #dao dao} und der {@link #mail mail} die Daten des Nutzers aus der Datenbank und
     * &uuml;bergibt sie dem {@link #nutzer nutzer}. Zus&auml;tslich wird die E-Mail-Adresse des {@code Nutzer}-Objekts
     * in der Session registriert, um durch den Filter passieren zu k&ouml;nnen.
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer bei erfolgreicher Anmeldung auf die
     *          {@code home.xhtml} weitergeleitet wird.
     * @throws ValidatorException wenn die E-Mail-Adresse oder das Passwort nicht richtig eingegeben werden.
     */
    public String login() throws ValidatorException {
        FacesContext context = FacesContext.getCurrentInstance();
        nutzer = dao.findNutzerByMail(mail);
        context.getExternalContext().getSessionMap().put("nutzer", nutzer.getMail());
        return "/nutzer/home?faces-redirect=true";
    }

    /**
     * Liefert das {@code Nutzer}-Objekt zur&uuml;ck, mit den Daten des angemeldeten Nutzers.
     *
     * @return {@code Nutzer}-Objekt mit den Daten des angemeldeten Nutzers.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

    /**
     * Ersetzt das {@code Nutzer}-Objekt durch das &uuml;bergebene {@code Nutzer}-Objekt.
     *
     * @param nutzer {@code Nutzer}-Objekt, welches das alte Objekt ersetzt.
     */
    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    /**
     * Liefert die eingegebene E-Mail-Adresse zur&uuml;ck.
     *
     * @return Eingegebene E-Mail-Adresse.
     */
    public String getMail() {
        return mail;
    }

    /**
     * &Uuml;bernimmt die E-Mail-Adresse, die eingegeben wurde.
     *
     * @param mail  Eingabe des Nutzers in das Feld f&uuml;r die E-Mail-Adresse.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Liefert das eingegebene Passwort zur&uuml;ck.
     *
     * @return Eingegebenes Passwort.
     */
    public String getPassword() {
        return password;
    }

    /**
     * &Uuml;bernimmt das Passwort, das eingegeben wurde.
     * @param password  Eingabe des Nutzers in das Feld f&uuml;r das Passwort.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
