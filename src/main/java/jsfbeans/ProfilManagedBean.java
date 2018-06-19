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
     * Falls der angemeldete Nutzer ein neues Passwort festgelegt hat, wird dieses in {@link #nutzer nutzer} reingeschrieben.
     * Die Daten des angemeldeten Nutzers werden aktualisiert.
     * Es wird gepr&uuml;ft, ob {@link #mail mail} ge&auml;ndert wurde.
     * Das {@link #nutzer nutzer}-Objekt wird in die Datenbank gemerged und {@link #refreshNutzer() refreshNutzer} aufgerufen.
     * </pre>
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der angemeldete Nutzer bei erfolgreicher Aktualisierung auf die {@code home.xhtml} weitergeleitet wird.
     */
    public String update() {
        if (!nutzer.getPasswort().equals(HashedPasswordGenerator.generateHash(password)) && !password.isEmpty()) {
            nutzer.setPasswort(password);
        }
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));
            updateSprachen();
            updateFreizeitaktivitaeten();
        if (!mail.equals(nutzer.getMail())) {
            nutzer.setMail(mail);
        }
            dao.merge(nutzer);
            refreshNutzer();
            return "home?faces-redirect=true";
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
     * @return Gibt die E-Mail-Adresse des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Ersetzt die E-Mail-Adresse des angemeldeten Nutzers durch eine neue E-Mail-Adresse.
     *
     * @param mail Die E-Mail-Adresse, welche die alte E-Mail-Adresse ersetzt.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Liefert die Bezirk-ID des angemeldeten Nutzers zur&uuml;ck.
     *
     * @return Gibt die Bezirk-ID des angemeldeten Nutzers zur&uuml;ck.
     */
    public int getBezirkID() {
        return bezirkID;
    }

    /**
     * Ersetzt die Bezirk-ID des angemeldeten Nutzers durch eine neue Bezirk-ID.
     *
     * @param bezirkID Die Bezirk-ID, welche die alte Bezirk-ID ersetzt.
     */
    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
    }

    /**
     * Erzeugt zuerst eine {@code ArrayList} und initialisiert sie mit dem sprachenSet der {@link #nutzer nutzer}-Variable.
     * Anschlie&szlig;end werden die einzelnen Sprachennamen in {@link #selectedSprachenString selectedSprachenString} als
     * eine Zeichenkette hinzugef&uuml;gt und der {@code String} zur&uuml;ckgegeben.
     *
     * @return Gibt eine Zeichenkette mit allen gesprochenen Sprachen des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedSprachenString() {

        ArrayList<Sprache> selectedSprachenList = new ArrayList<>(nutzer.getSprachenSet());
        String[] selectedSprachenArray = new String[selectedSprachenList.size()];
        for (int i = 0; i < selectedSprachenList.size(); i++) {
            selectedSprachenArray[i] = String.valueOf(selectedSprachenList.get(i).getId());
        }
        selectedSprachenString = String.join(",", selectedSprachenArray);
        return selectedSprachenString;
    }

    /**
     * Ersetzt die Zeichenkette {@link #selectedSprachenString selectedSprachenString} durch eine neue Zeichenkette mit
     * selektierten Sprachen des angemeldeten Nutzers.
     *
     * @param selectedSprachenString Der {@code String}, welcher den alten {@code String}-Wert ersetzt.
     */
    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    /**
     * Initialisiert {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} durch aufrufen der statischen
     * Methode {@code selectedFreizeitaktivitaetenString} der Klasse {@code FreizeitaktivitaetenStringConverter} und
     * gibt den {@code String} zur&uuml;ck.
     *
     * @return Gibt eine Zeichenkette mit allen Freizeitaktivitäten des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedFreizeitaktivitaetenString() {
        selectedFreizeitaktivitaetenString = FreizeitaktivitaetenStringConverter.selectedFreizeitaktivitaetenString(nutzer);
        return selectedFreizeitaktivitaetenString;
    }

    /**
     * Ersetzt die Zeichenkette {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} durch eine neue Zeichenkette mit
     * selektierten Freizeitaktivitäten des angemeldeten Nutzers.
     *
     * @param selectedFreizeitaktivitaetenString Der {@code String}, welcher den alten {@code String}-Wert ersetzt.
     */
    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    /**
     * Liefert das neue Passwort des angemeldeten Nutzers zur&uuml;ck, falls dieser ein neues eingegeben hat.
     *
     * @return Eingegebenes Passwort des angemeldeten Nutzers.
     */
    public String getPassword() {
        return password;
    }

    /**
     * &Uuml;bernimmt das neue Passwort, welches eingegeben wurde.
     *
     * @param password Eingabe des Nutzers in das Feld f&uuml;r das Passwort.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der {@code LoginManagedBean} solange existiert, wie
     * die Session l&auml;uft. Anschließend wird das {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }

    /**
     * Leert das {@code sprachenSet} des angemeldeten Nutzers und f&uuml;gt die selektierten Sprachen aus dem {@code String}
     * {@link #selectedSprachenString selectedSprachenString} dem angemeldeten Nutzer wieder hinzu.
     */
    private void updateSprachen() {
        nutzer.clearSprachenSet();
        List<Sprache> selectedSprachenList = new ArrayList<>();
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByID(Integer.parseInt(aSelectedSprachenArray)));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    /**
     * Leert das {@code freizeitaktivitaetenSet} des angemeldeten Nutzers und f&uuml;gt die selektierten Freizeitaktivitäten aus dem {@code String}
     * {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} dem angemeldeten Nutzer wieder hinzu.
     */
    private void updateFreizeitaktivitaeten() {
        nutzer.clearFreizeitaktivitaetenSet();
        List<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(Integer.parseInt(aSelectedFreizeitaktivitaetenArray)));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    /**
     * Ersetzt das {@code Nutzer}-Objekt der {@code LoginManagedBean}, mit dem aktualisierten {@code Nutzer}-Objekt und
     * "refreshed" ihn damit.
     */
    private void refreshNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.setNutzer(dao.findNutzerByMail(nutzer.getMail()));
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
