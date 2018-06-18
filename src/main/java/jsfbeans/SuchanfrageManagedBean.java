package jsfbeans;

import dao.DAO;
import models.Nutzer;
import models.Suchanfrage;
import utilities.FreizeitaktivitaetenStringConverter;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Die {@code SuchanfrageManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r
 * die {@code suchanfrage.xhtml}.
 */
@ManagedBean
@SessionScoped
public class SuchanfrageManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                     dao;

    /**
     * Das {@code Nutzer}-Objekt, welches den angemeldeten Nutzer darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Nutzer                  nutzer;

    /**
     * {@code String}, welcher die Namen der Freizeitaktivit&auml;ten enth&auml;lt, die der angemeldete Nutzer hat.
     */
    private String                  selectedFreizeitaktivitaetenString;

    /**
     * Das {@code Suchanfrage}-Objekt, welches im Konstruktor instanziiert wird und in den die Suchparameter
     * des angemeldeten Nutzers geschrieben werden.
     */
    private Suchanfrage             suchanfrage;

    /**
     * Die {@code ArrayList} mit der Typisierung {@code Suchanfrage} enth&auml;lt die gespeicherten Suchanfragen des angemeldeten Nutzers.
     */
    private ArrayList<Suchanfrage>  suchanfrageArrayList;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code SuchanfrageManagedBean}-Objekt und ruft dabei die Methode {@link #initNutzer() initNutzer} auf
     * und instanziiert die Variable {@link #suchanfrage suchanfrage}.
     */
    public SuchanfrageManagedBean() {
        this.nutzer         = initNutzer();
        this.suchanfrage    = new Suchanfrage();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Pr&uuml;ft nach dem Wert der Methode {@link #validateSuchanfrage() validateSuchanfrage} und ob die L&auml;nge
     * der Liste aus {@code dao.findSuchanfrageByNutzer} kleiner als 5 ist. Falls ja, wird {@link #nutzer} {@link #suchanfrage suchanfrage}
     * hinzugef&uuml;gt und {@link #suchanfrage} wird in die Datenbank gemerged. Anschlie&szlig;end wird der angemeldete Nutzer
     * zur {@code suchergebnisse.xhtml} weitergeleitet.
     * Falls die if-Anweisung false ergibt, wird {@link #suchanfrage suchanfrage} nicht in die Datenbank gemerged. Es findet
     * nur eine Weiterleitung des angemeldeten Nutzers statt.
     *
     * @return Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer auf die {@code suchergebnisse.xhtml} weitergeleitet wird.
     */
    public String search() {
        if (!validateSuchanfrage() && dao.findSuchanfrageByNutzer(nutzer).size()<5) {
            suchanfrage.addNutzer(nutzer);
            dao.merge(suchanfrage);
        }
            return "suchergebnisse?faces-redirect=true";
    }

    /**
     * F&uuml;hrt die Anfrage aus, um eine Suchanfrage-Entit&auml;t aus der Datenbank zu l&ouml;schen, welche dem &uuml;gerbegenem
     * {@code Suchanfrage}-Objekt entspricht.
     *
     * @param savedSuchanfrage Das {@code Suchanfrage}-Objekt, welches der zu l&ouml;schenden Entit&auml;t aus der Datenbank entspricht.
     */
    public void deleteSuchanfrage(Suchanfrage savedSuchanfrage) {
        dao.deleteSuchanfrageNQ(savedSuchanfrage);
    }

    /**
     * &Uuml;berschreibt {@link #suchanfrage suchanfrage} mit dem &uuml;bergebenem {@code Suchanfrage}-Objekt und
     * leitet den angemeldeten Nutzer weiter zur {@code suchergebnisse.xhtml}.
     *
     * @param   savedSuchanfrage Das {@code Suchanfrage}-Objekt, welches {@link #suchanfrage suchanfrage} &uuml;berschreibt.
     * @return  Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer auf die {@code suchergebnisse.xhtml} weitergeleitet wird.
     */
    public String useSuchanfrage(Suchanfrage savedSuchanfrage) {
        this.suchanfrage = savedSuchanfrage;
        return "suchergebnisse?faces-redirect=true";
    }

    /**
     * Gibt Anhand einer Sprach-ID den Sprachennamen als {@code String} zur&uuml;ck.
     * Diese Methode wird dazu genutzt, um in der Tabelle der {@code suchanfrage.xhtml} die Sprachennamen anzuzeigen.
     *
     * @param spracheID Sprach-ID des gesuchten Sprachennamens
     * @return Gibt den Namen der gesuchten Sprache zur&uuml;ck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
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

    // todo CCE : Das selectMultiMenu in der suchanfrage.xhtml muss soweit bearbeitet werden,
    // TODO Joe: 2018-06-18  dass keine eingaben in ihr erfolgen koennen und dann kann diese Methode geloescht werden.
    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    /**
     * Liefert das {@code Suchanfrage}-Objekt zur&uuml;ck, mit den Suchparametern des angemeldeten Nutzers.
     *
     * @return {@code Suchanfrage}-Objekt mit den Suchparametern des angemeldeten Nutzers.
     */
    public Suchanfrage getSuchanfrage() {
        return suchanfrage;
    }

    /**
     * Ersetzt das {@code Suchanfrage}-Objekt durch das &uuml;bergebene {@code Suchanfrage}-Objekt.
     *
     * @param suchanfrage {@code Suchanfrage}-Objekt, welches das alte Objekt ersetzt.
     */
    public void setSuchanfrage(Suchanfrage suchanfrage) {
        this.suchanfrage = suchanfrage;
    }

    /**
     * Instanziiert {@link #suchanfrageArrayList suchanfrageArrayList}, f&uuml;gt &uuml;ber {@link #dao dao}
     * die Liste der gespeicherten Suchanfragen des angemeldeten Nutzers und gibt
     * {@link #suchanfrageArrayList suchanfrageArrayList} zur&uuml;ck.
     *
     * @return Liste mit den gespeicherten Suchanfragen des angemeldeten Nutzers.
     */
    public ArrayList<Suchanfrage> getSuchanfrageArrayList() {
        suchanfrageArrayList = new ArrayList<>();
        suchanfrageArrayList = dao.findSuchanfrageByNutzer(nutzer);
        return this.suchanfrageArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der {@code LoginManagedBean} solange existiert, wie
     * die Session l&auml;uft. Anschließend wird das {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
        return nutzer;
    }

    /**
     * Pr&uuml;ft, ob der angemeldete Nutzer eine gespeicherte Suchanfrage in der Datenbank hat, die gleich {@link #suchanfrage suchanfrage} ist.
     * Wenn ja gibt die Methode ein true zur&uuml;ck. Falls eine {@code NullPointerException} fliegt, wird ein false zur&uuml;ckgegeben.
     *
     * @return Gibt true zur&uuml;ck, wenn {@link #suchanfrage suchanfrage} bereits in der Datenbank existiert und false, wenn
     *         eine NullPointerException fliegt.
     */
    private boolean validateSuchanfrage() {
        try {
            Suchanfrage existingSuchanfrage = dao.findSuchanfrage(suchanfrage, nutzer);
            return existingSuchanfrage.getParamSpracheID() == suchanfrage.getParamSpracheID() &&
                    existingSuchanfrage.getNutzer().getId() == nutzer.getId();
        } catch (NullPointerException e) {
            return false;
        }
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
