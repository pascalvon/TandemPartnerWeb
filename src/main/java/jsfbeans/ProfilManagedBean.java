package jsfbeans;

import dao.DAO;
import models.Bezirk;
import models.Freizeitaktivitaeten;
import models.Nutzer;
import models.Sprache;
import utilities.FreizeitaktivitaetenStringConverter;

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
    private DAO                             dao;

    /**
     * Das {@code Nutzer}-Objekt, welches den angemeldeten Nutzer darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Nutzer                          nutzer;

    /**
     * {@code String}, welcher die E-Mail-Adresse des angemeldeten Nutzers enth&auml;lt.
     */
    private String                          mail;


    private String                          vorname;

    private String                          nachname;

    /**
     * {@code String}-Wert, welcher den Bezirksnamen des angemeldeten Nutzers enth&auml;lt.
     */
    private String                          bezirkName;

    /**
     * Eine Liste mit allen Bezirk-Entit&auml;ten aus der Datenbank in Form von {@code Bezirk}-Objekten.
     */
    private ArrayList<Bezirk>               allBezirkList;

    /**
     * {@code String}, welcher die Namen der Sprachen enth&auml;lt, die der angemeldete Nutzer spricht.
     */
    private String                          selectedSprachenString;

    /**
     * Eine Liste mit allen Sprache-Entit&auml;ten aus der Datenbank in Form von {@code Sprache}-Objekten.
     */
    private ArrayList<Sprache>              allSprachenList;

    /**
     * {@code String}, welcher die Namen der Freizeitaktivit&auml;ten enth&auml;lt, die der angemeldete Nutzer hat.
     */
    private String                          selectedFreizeitaktivitaetenString;

    /**
     * Eine Liste mit allen Freizeitaktivitaeten-Entit&auml;ten aus der Datenbank in Form von
     * {@code Freizeitaktivitaeten}-Objekten.
     */
    private ArrayList<Freizeitaktivitaeten> allFreizeitaktivitaetenList;

    /**
     * {@code String}, f&uuml;r die Eingabe eines neuen Passworts des angemeldeten Nutzers.
     */
    private String                          password;

    // ============================  Constructors  ===========================79

    /**
     * Initialisiert ein neu erzeugtes {@code ProfilManagedBean}-Objekt, ruft dabei die Methode
     * {@link #initNutzer() initNutzer} auf und wei&szlig;t den Variablen {@link #mail mail} und
     * {@link #bezirkName bezirkName} die jeweiligen Werte vom vorher initialisierten {@link #nutzer nutzer} zu.
     */
    public ProfilManagedBean() {
        initNutzer();
        this.mail       = nutzer.getMail();
        this.bezirkName = nutzer.getBezirk().getBezirkName();
    }

    /**
     * Initialisiert ein {@code ProfilManagedBean}-Objekt mit den eingegebenen Parametern und weißt diese
     * den entsprechenden Variablen zu. Dieser Konstruktor wird ausschlie&szlig;ig f%uuml;r die Unittests ben&ouml;tigt.
     *
     * @param dao {@code DAO}-Objekt, welches dem {@code DAO}-Objekt {@link #dao dao} zugewiesen
     *                         werden soll.
     * @param nutzer {@code Nutzer}-Objekt, welches dem {@code Nutzer}-Objekt {@link #nutzer nutzer} zugewiesen
     *                             werden soll.
     */
    ProfilManagedBean(DAO dao, Nutzer nutzer) {
        this.dao    = dao;
        this.nutzer = nutzer;
    }

    // ===========================  public  Methods  =========================79
    /**
     * <pre>
     * Aktualisiert die Daten des angemeldeten Nutzers.
     * Falls der angemeldete Nutzer ein neues Passwort festgelegt hat, wird dieses in {@link #nutzer nutzer}
     * reingeschrieben. Die Daten des angemeldeten Nutzers werden aktualisiert.
     * Es wird gepr&uuml;ft, ob {@link #mail mail} ge&auml;ndert wurde. Das {@link #nutzer nutzer}-Objekt wird in
     * die Datenbank gemerged und {@link #refreshNutzer() refreshNutzer} aufgerufen.
     * </pre>
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der angemeldete Nutzer bei erfolgreicher
     *          Aktualisierung auf die {@code home.xhtml} weitergeleitet wird.
     */
    public String update() {
        if (!nutzer.getPasswort().equals(password) && !password.isEmpty()) {
            nutzer.setPasswort(password);
        }
        nutzer.setVorname(vorname);
        nutzer.setNachname(nachname);
        nutzer.setBezirk(dao.findBezirkByName(bezirkName));
        updateSprachen();
        updateFreizeitaktivitaeten();
        if (!mail.equals(nutzer.getMail())) {
            nutzer.setMail(mail);
        }
            dao.merge(nutzer);
        if (contextExists()){
            refreshNutzer();
        }
            return "home?faces-redirect=true";
    }

    /**
     * L&ouml;scht den angemeldeten Nutzer aus der Datenbank. Vorher werden alle Suchanfrage- und
     * Matchanfragen-Entit&auml;ten, welche die ID des angemeldeten Nutzers enthalten gel&ouml;scht.
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer bei erfolgreicher L&ouml;schung auf die
     *          {@code login.xhtml} weitergeleitet wird.
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

    public String getVorname() {
        vorname = nutzer.getVorname();
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        nachname = nutzer.getNachname();
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Liefert den Bezirksnamen des angemeldeten Nutzers zur&uuml;ck.
     *
     * @return Gibt den Bezirksnamen des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getBezirkName() {
        return bezirkName;
    }

    /**
     * Ersetzt den Bezirksnamen des angemeldeten Nutzers durch einen neuen Bezirksnamen.
     *
     * @param bezirkName Der Bezirksname, welcher den alten Bezirksnamen ersetzt.
     */
    public void setBezirkName(String bezirkName) {
        this.bezirkName = bezirkName;
    }

    /**
     * Instanziiert die Liste {@link #allBezirkList allBezirkList} und bef&uuml;llt sie, durch Aufruf der Methode
     * findBezirkList des {@code DAO}-Objektes {@link #dao dao}.
     *
     * @return  Gibt eine Liste mit allen Bezirk-Entit&auml;ten aus der Datenbank in Form von {@code Bezirk}-Objekten
     *          zur&uuml;ck.
     */
    public ArrayList<Bezirk> getAllBezirkList() {
        allBezirkList = new ArrayList<>();
        allBezirkList = dao.findBezirkList();
        return allBezirkList;
    }

    /**
     * Erzeugt zuerst eine {@code ArrayList} und initialisiert sie mit dem sprachenSet der
     * {@link #nutzer nutzer}-Variable.
     * Anschlie&szlig;end werden die einzelnen Sprachennamen in {@link #selectedSprachenString selectedSprachenString}
     * als eine Zeichenkette hinzugef&uuml;gt und der {@code String} zur&uuml;ckgegeben.
     *
     * @return Gibt eine Zeichenkette mit allen gesprochenen Sprachen des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedSprachenString() {
        ArrayList<Sprache> selectedSprachenList = new ArrayList<>(nutzer.getSprachenSet());
        String[] selectedSprachenArray = new String[selectedSprachenList.size()];
        for (int i = 0; i < selectedSprachenList.size(); i++) {
            selectedSprachenArray[i] = String.valueOf(selectedSprachenList.get(i));
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
     * Instanziiert die Liste {@link #allSprachenList allSprachenList} und bef&uuml;llt sie, durch Aufruf der Methode
     * findSpracheList des {@code DAO}-Objektes {@link #dao dao}.
     *
     * @return  Gibt eine Liste mit allen Sprache-Entit&auml;ten aus der Datenbank in Form von {@code Sprache}-Objekten
     *          zur&uuml;ck.
     */
    public ArrayList<Sprache> getAllSprachenList() {
        allSprachenList = new ArrayList<>();
        allSprachenList = dao.findSpracheList();
        return allSprachenList;
    }

    /**
     * Initialisiert {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} durch aufrufen der
     * statischen Methode {@code selectedFreizeitaktivitaetenString} der Klasse
     * {@code FreizeitaktivitaetenStringConverter} und gibt den {@code String} zur&uuml;ck.
     *
     * @return Gibt eine Zeichenkette mit allen Freizeitaktivitäten des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedFreizeitaktivitaetenString() {
        selectedFreizeitaktivitaetenString = FreizeitaktivitaetenStringConverter.selectedFreizeitaktivitaetenString(nutzer);
        return selectedFreizeitaktivitaetenString;
    }

    /**
     * Ersetzt die Zeichenkette {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} durch
     * eine neue Zeichenkette mit selektierten Freizeitaktivitäten des angemeldeten Nutzers.
     *
     * @param selectedFreizeitaktivitaetenString Der {@code String}, welcher den alten {@code String}-Wert ersetzt.
     */
    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    /**
     * Instanziiert die Liste {@link #allFreizeitaktivitaetenList allFreizeitaktivitaetenList} und bef&uuml;llt sie,
     * durch Aufruf der Methode findFreizeitaktivitaetenList des {@code DAO}-Objektes {@link #dao dao}.
     * @return  Gibt eine Liste mit allen Freizeitaktivitaeten-Entit&auml;ten aus der Datenbank in Form von
     *          {@code Freizeitaktivitaeten}-Objekten zur&uuml;ck.
     */
    public ArrayList<Freizeitaktivitaeten> getAllFreizeitaktivitaetenList() {
        allFreizeitaktivitaetenList = new ArrayList<>();
        allFreizeitaktivitaetenList = dao.findFreizeitaktivitaetenList();
        return allFreizeitaktivitaetenList;
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
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der
     * {@code LoginManagedBean} solange existiert, wie die Session l&auml;uft. Anschließend wird das
     * {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }

    /**
     * Leert das {@code sprachenSet} des angemeldeten Nutzers und f&uuml;gt die selektierten Sprachen aus dem
     * {@code String} {@link #selectedSprachenString selectedSprachenString} dem angemeldeten Nutzer wieder hinzu.
     */
    private void updateSprachen() {
        nutzer.clearSprachenSet();
        List<Sprache> selectedSprachenList = new ArrayList<>();
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByName(aSelectedSprachenArray));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    /**
     * Leert das {@code freizeitaktivitaetenSet} des angemeldeten Nutzers und f&uuml;gt die selektierten
     * Freizeitaktivitäten aus dem {@code String}
     * {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} dem angemeldeten Nutzer wieder
     * hinzu.
     */
    private void updateFreizeitaktivitaeten() {
        nutzer.clearFreizeitaktivitaetenSet();
        List<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>();
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByName(aSelectedFreizeitaktivitaetenArray));
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

    /**
     * Pr&uuml;ft ob es einen {@code FacesContext} gibt. Wenn ja, wird true zur&uuml;ckgegeben. Falls es keinen
     * {@code FacesContext} gibt, wird false zur&uuml;ckgegeben.
     * @return  Gibt true zur&uuml;ck, wenn es ein {@code FacesContext} gibt, andernfalls false.
     */
    private boolean contextExists() {
        try {
            return FacesContext.getCurrentInstance() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
