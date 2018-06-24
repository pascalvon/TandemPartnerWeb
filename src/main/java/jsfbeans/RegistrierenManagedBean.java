package jsfbeans;

import dao.DAO;
import models.*;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Die {@code RegistrierenManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r die
 * {@code profil.xhtml}.
 */
@ManagedBean
@RequestScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                             dao;

    /**
     * Das {@code Nutzer}-Objekt, welches im Konstruktor initialisiert wird und in dem die Eingaben des Nutzers
     * gehalten werden.
     */
    private Nutzer                          nutzer;

    /**
     * {@code String}-Wert, in dem der Bezirksname des Nutzers nach der Eingabe gehalten wird.
     */
    private String                          bezirkName;

    /**
     * Eine Liste mit allen Bezirk-Entit&auml;ten aus der Datenbank in Form von {@code Bezirk}-Objekten.
     */
    private ArrayList<Bezirk>               allBezirkList;

    /**
     * Ein {@code List}-Objekt mit der Typisierung {@code Sprache}, welches im Konstruktor instanziiert wird und
     * in dem die {@code Sprache}-Objekte gehalten werden, die den Sprachennamen
     * aus {@link #selectedSprachenString selectedSprachenString} entsprechen.
     */
    private List<Sprache>                   selectedSprachenList;

    /**
     * {@code String}, in dem die Namen der Sprachen gehalten werden, die der Nutzer ausw&auml;hlt.
     */
    private String                          selectedSprachenString;

    /**
     * Eine Liste mit allen Sprache-Entit&auml;ten aus der Datenbank in Form von {@code Sprache}-Objekten.
     */
    private ArrayList<Sprache>              allSprachenList;

    /**
     * Ein {@code List}-Objekt mit der Typisierung {@code Freizeitaktivitaeten}, welches im Konstruktor instanziiert
     * wird und in dem die {@code Freizeitaktivitaeten}-Objekte gehalten werden, die den Namen der Freizeitaktivitaeten
     * aus {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} entsprechen.
     */
    private List<Freizeitaktivitaeten>      selectedFreizeitaktivitaetenList;

    /**
     * {@code String}, in dem die Namen der Freizeitaktivitaeten gehalten werden, die der Nutzer ausw&auml;hlt.
     */
    private String                          selectedFreizeitaktivitaetenString;

    /**
     * Eine Liste mit allen Freizeitaktivitaeten-Entit&auml;ten aus der Datenbank in Form von
     * {@code Freizeitaktivitaeten}-Objekten.
     */
    private ArrayList<Freizeitaktivitaeten> allFreizeitaktivitaetenList;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code ProfilManagedBean}-Objekt und instanziiert {@link #nutzer nutzer},
     * {@link #selectedSprachenList selectedSprachenList} und
     * {@link #selectedFreizeitaktivitaetenList selectedFreizeitaktivitaetenList}.
     */
    public RegistrierenManagedBean() {
        this.nutzer                             = new Nutzer();
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    /**
     * Initialisiert ein {@code RegistrierenManagedBean}-Objekt mit den eingegebenen Parametern und weißt diese
     * den entsprechenden Variablen zu. Dieser Konstruktor wird ausschlie&szlig;ig f%uuml;r die Unittests ben&ouml;tigt.
     *
     * @param dao {@code DAO}-Objekt, welches dem {@code DAO}-Objekt {@link #dao dao} zugewiesen
     *                         werden soll.
     * @param nutzerID {@code int}-Wert, welcher dem {@code Nutzer}-Objekt {@link #nutzer nutzer} &uuml;bergeben
     *                             werden soll.
     */
    public RegistrierenManagedBean(DAO dao, int nutzerID) {
        this.dao                                = dao;
        this.nutzer                             = new Nutzer(nutzerID);
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Erstellt eine neue Nutzer-Entit&auml;t in der Datenbank.
     * Daf%uuml;r wird dem {@link #nutzer nutzer} &uuml;ber die {@link #bezirkName bezirkName} ein {@code Bezirk}-Objekt
     * hinzugef&uuml;gt. Danach werden die Methoden {@link #addSprachenToNutzer() addSprachenToNutzer} und
     * {@link #addFreizeitaktivitaetenToNutzer() addFreizeitaktivitaetenToNutzer} aufgerufen.
     * Anschlie&szlig;end wird der {@link #nutzer nutzer} in die Datenbank gemerged und die Methode
     * {@link #initNutzer() initNutzer} aufgerufen.
     *
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer bei erfolgreicher Registrierung auf die
     *          {@code home.xhtml} weitergeleitet wird.
     */
    public String register() {
            nutzer.setBezirk(dao.findBezirkByName(bezirkName));
            addSprachenToNutzer();
            addFreizeitaktivitaetenToNutzer();
            dao.merge(nutzer);
            if (contextExists()) {
                initNutzer();
            }
            return "/nutzer/home?faces-redirect=true";
    }

    /**
     * Liefert das {@code Nutzer}-Objekt zur&uuml;ck, welches die Eingaben des Nutzers enth&auml;lt.
     *
     * @return {@code Nutzer}-Objekt mit den Eingaben des Nutzers.
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
     * Liefert den Bezirksnamen des Nutzers zur&uuml;ck.
     *
     * @return Gibt den Bezirksanmen des Nutzers zur&uuml;ck.
     */
    public String getBezirkName() {
        return bezirkName;
    }

    /**
     * Ersetzt den Bezirksnamen des Nutzers durch einen neuen Bezirksnamen.
     *
     * @param bezirkName Der Bezirksname, welcher den alten Bezirksnamen ersetzt.
     */
    public void setBezirkName(String bezirkName) {
        this.bezirkName = bezirkName;
    }

    /**
     * Instanziiert die Liste {@link #allBezirkList allBezirkList} und bef&uuml:llt sie, durch Aufruf der Methode
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
     * Liefert den {@code String} mit den selektierten Sprachen des Nutzers zur&uuml;ck.
     *
     * @return Gibt den {@code String} mit den selektierten Sprachen des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedSprachenString() {
        return selectedSprachenString;
    }

    /**
     * Ersetzt den {@code String} mit den selektierten Sprachen des Nutzers durch einen neue {@code String}.
     *
     * @param selectedSprachenString Der {@code String} mit den selektierten Sprachen, welche den alten {@code String}
     *                               ersetzen.
     */
    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }


    /**
     * Instanziiert die Liste {@link #allSprachenList allSprachenList} und bef&uuml:llt sie, durch Aufruf der Methode
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
     * Liefert den {@code String} mit den selektierten Freizeitaktivitaeten des Nutzers zur&uuml;ck.
     *
     * @return Gibt den {@code String} mit den selektierten Freizeitaktivitaeten des angemeldeten Nutzers zur&uuml;ck.
     */
    public String getSelectedFreizeitaktivitaetenString() {
        return selectedFreizeitaktivitaetenString;
    }

    /**
     * Ersetzt den {@code String} mit den selektierten Freizeitaktivitaeten des Nutzers durch einen neue {@code String}.
     *
     * @param selectedFreizeitaktivitaetenString Der {@code String} mit den selektierten Freizeitaktivitaeten,
     *                                           welche den alten {@code String} ersetzen.
     */
    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    /**
     * Instanziiert die Liste {@link #allFreizeitaktivitaetenList allFreizeitaktivitaetenList} und bef&uuml:llt sie,
     * durch Aufruf der Methode findFreizeitaktivitaetenList des {@code DAO}-Objektes {@link #dao dao}.
     * @return  Gibt eine Liste mit allen Freizeitaktivitaeten-Entit&auml;ten aus der Datenbank in Form von
     *          {@code Freizeitaktivitaeten}-Objekten zur&uuml;ck.
     */
    public ArrayList<Freizeitaktivitaeten> getAllFreizeitaktivitaetenList() {
        allFreizeitaktivitaetenList = new ArrayList<>();
        allFreizeitaktivitaetenList = dao.findFreizeitaktivitaetenList();
        return allFreizeitaktivitaetenList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Wandelt {@link #selectedSprachenString selectedSprachenString} um in eine Liste von {@code Sprache}-Objekten und
     * f&uuml;gt diese Objekte dem {@link #nutzer nutzer} hinzu.
     */
    private void addSprachenToNutzer() {
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByName(aSelectedSprachenArray));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    /**
     * Wandelt {@link #selectedFreizeitaktivitaetenString selectedFreizeitaktivitaetenString} um in eine Liste von
     * {@code Freizeitaktivitaeten}-Objekten und f&uuml;gt diese Objekte dem {@link #nutzer nutzer} hinzu.
     */
    private void addFreizeitaktivitaetenToNutzer() {
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByName(aSelectedFreizeitaktivitaetenArray));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    /**
     * Erzeugt eine {@code LoginManagedBean} und f&uuml;gt ihrem {@code Nutzer}-Objekt {@link #nutzer nutzer} hinzu.
     * Zus&auml;tslich wird die E-Mail-Adresse des {@code Nutzer}-Objekts in der Session registriert, um durch
     * den Filter passieren zu k&ouml;nnen.
     */
    private void initNutzer() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.setNutzer(dao.findNutzerByMail(nutzer.getMail()));
        context.getExternalContext().getSessionMap().put("nutzer", nutzer.getMail());

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
