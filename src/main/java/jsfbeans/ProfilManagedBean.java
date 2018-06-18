package jsfbeans;

import dao.DAO;
import models.Freizeitaktivitaeten;
import models.Nutzer;
import models.Sprache;
import utilities.FreizeitaktivitaetenStringConverter;
import utilities.HashedPasswordGenerator;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Die {@code ProfilManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r die {@code profil.xhtml}.
 */
@ManagedBean
@ViewScoped
public class ProfilManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO         dao;

    /**
     * Das {@code Nutzer}-Objekt, welches den angemeldeten Nutzer darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Nutzer      nutzer;

    /**
     * {@code String}, welcher die E-Mail-Adresse des angemeldeten Nutzers enth&auml;lt.
     */
    private String      mail;

    /**
     * {@code int}-Wert, welcher die Bezirk-ID des angemeldeten Nutzers enth&auml;lt.
     */
    private int         bezirkID;

    /**
     * {@code String}, welcher die Namen der Sprachen enth&auml;lt, die der angemeldete Nutzer spricht.
     */
    private String      selectedSprachenString;

    /**
     * {@code String}, welcher die Namen der Freizeitaktivit&auml;ten enth&auml;lt, die der angemeldete Nutzer hat.
     */
    private String      selectedFreizeitaktivitaetenString;

    /**
     * {@code String}, f&uuml;r die Eingabe eines neuen Passworts des angemeldeten Nutzers.
     */
    private String      password;

    // ============================  Constructors  ===========================79

    /**
     * Initialisiert ein neu erzeugtes {@code ProfilManagedBean}-Objekt, ruft dabei die Methode {@link #initNutzer() initNutzer} auf und
     * wei&szlig;t den Variablen {@link #mail mail} und {@link #bezirkID bezirkID} die jeweiligen Werte vom vorher initialisierten {@link #nutzer nutzer} zu.
     */
    public ProfilManagedBean() {
        initNutzer();
        this.mail       = nutzer.getMail();
        this.bezirkID   = nutzer.getBezirk().getId();
    }

    // ===========================  public  Methods  =========================79
    /**
     * <pre>
     * Aktualisiert die Daten des angemeldeten Nutzers.
     * Unter anderem werden folgende if-else-Anweisungen durchgegangen:
     *  1. Falls der angemeldete Nutzer ein neues Passwort festgelegt hat, wird dieses in {@link #nutzer nutzer} reingeschrieben.
     *  2. Falls der angemeldete Nutzer seine E-Mail-Adresse nicht aktualisiert, werden seine Angaben aktualisiert,
     *     das {@link #nutzer nutzer}-Objekt in die Datenbank gemerged und {@link #refreshNutzer() refreshNutzer} aufgerufen.
     *  3. Falls der angemeldete Nutzer seine E-Mail-Adresse aktualisiert, wird neben den Schritten aus 2. noch die E-Mail-Adresse
     *     des angemeldeten Nutzers aktualisiert.
     * </pre>
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der angemeldete Nutzer bei erfolgreicher Aktualisierung auf die {@code home.xhtml} weitergeleitet wird.
     */
    public String update() {
        if (!nutzer.getPasswort().equals(HashedPasswordGenerator.generateHash(password)) && !password.isEmpty()) {
            nutzer.setPasswort(password);
        }
        if (mail.equals(nutzer.getMail())) {
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));
            updateSprachen();
            updateFreizeitaktivitaeten();
            dao.merge(nutzer);
            refreshNutzer();
            return "home?faces-redirect=true";

        } else{
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));
            updateSprachen();
            updateFreizeitaktivitaeten();
            nutzer.setMail(mail);
            dao.merge(nutzer);
            refreshNutzer();
            return "home?faces-redirect=true";
        }
    }

    /**
     * L&ouml;scht den angemeldeten Nutzer aus der Datenbank. Vorher werden alle Suchanfrage- und Matchanfragen-Entit&auml;ten,
     * welche die ID des angemeldeten Nutzers enthalten gel&ouml;scht.
     *
     * @return Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer bei erfolgreicher L&ouml;schung auf die {@code login.xhtml} weitergeleitet wird.
     */
    public String deleteNutzer() {
        dao.deleteSuchanfrageByNutzer(nutzer);
        dao.deleteMatchanfrageByNutzer(nutzer);
        dao.deleteNutzer(nutzer);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
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
     * Liefert die E-Mail-Adresse des angemeldeten Nutzers zur&uuml;ck.
     *
     * @return E-Mail-Adresse des angemeldeten Nutzers.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Ersetzt die E-Mail-Adresse des angemeldeten Nutzers durch eine Neue.
     *
     * @param mail Die E-Mail-Adresse, welche die alte E-Mail-Adresse ersetzt.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Liefert die Bezirk-ID des angemeldeten Nutzers zur&uuml;ck.
     *
     * @return Bezirk-ID des angemeldeten Nutzers.
     */
    public int getBezirkID() {
        return bezirkID;
    }

    /**
     *
     * @param bezirkID
     */
    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
    }

    public String getSelectedSprachenString() {

        ArrayList<Sprache> selectedSprachenList = new ArrayList<>(nutzer.getSprachenSet());
        String[] selectedSprachenArray = new String[selectedSprachenList.size()];
        for (int i = 0; i < selectedSprachenList.size(); i++) {
            selectedSprachenArray[i] = String.valueOf(selectedSprachenList.get(i).getId());
        }
        selectedSprachenString = String.join(",", selectedSprachenArray);
        return selectedSprachenString;
    }

    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        selectedFreizeitaktivitaetenString = FreizeitaktivitaetenStringConverter.selectedFreizeitaktivitaetenString(nutzer);
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
        // todo CCE : JavaDoc initNutzer aktualisieren
    }

    private void updateSprachen() {
        nutzer.clearSprachenSet();
        List<Sprache> selectedSprachenList = new ArrayList<>();       // todo CCE : checken ob instanziiert oder initialisiert genutzt wird
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByID(Integer.parseInt(aSelectedSprachenArray)));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    private void updateFreizeitaktivitaeten() {
        nutzer.clearFreizeitaktivitaetenSet();
        ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();       // todo CCE : checken ob instanziiert oder initialisiert genutzt wird
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(Integer.parseInt(aSelectedFreizeitaktivitaetenArray)));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    private void refreshNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.setNutzer(dao.findNutzerByMail(nutzer.getMail()));
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
