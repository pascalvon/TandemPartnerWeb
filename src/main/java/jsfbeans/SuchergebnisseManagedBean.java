package jsfbeans;

import dao.DAO;
import models.*;
import utilities.AgeCalculator;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Die {@code SuchergebnisseManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r
 * die {@code suchanfrage.xhtml}.
 */
@ManagedBean
@ViewScoped
public class SuchergebnisseManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                             dao;

    /**
     * Das {@code Suchanfrage}-Objekt, welches die vom angemeldeten Nutzer ausgef&uuml;hrte Suchanfrage darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Suchanfrage                     suchanfrage;

    /**
     * Das {@code Nutzer}-Objekt, welches den angemeldeten Nutzer darstellt, das im Konstruktor
     * durch die Methode {@link #initNutzer() initNutzer} initialisiert wird.
     */
    private Nutzer                          nutzer;

    /**
     * Die {@code ArrayList} mit der Typisierung {@code SuchergebnisModel} enth&auml;lt alle vorgeschlagenen Nutzer, die
     * der Suchanfrage des angemeldeten Nutzers entsprechen.
     */
    private ArrayList<SuchergebnisModel>    suchergebnisseArrayList;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code SuchergebnisseManagedBean}-Objekt und ruft dabei die Methoden
     * {@link #initSuchanfrage() initSuchanfrage} und {@link #initNutzer() initNutzer} auf.
     */
    public SuchergebnisseManagedBean() {
        initSuchanfrage();
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Gibt Anhand einer Sprach-ID den Sprachennamen als {@code String} zur&uuml;ck.
     * Diese Methode wird dazu genutzt, um in der Tabelle der {@code suchergebnisse.xhtml} die Sprachennamen anzuzeigen.
     *
     * @param spracheID Sprach-ID des gesuchten Sprachennamens
     * @return Gibt den Namen der gesuchten Sprache zur&uuml;ck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
    }

    /**
     * Pr&uuml;ft, ob bereits eine Matchanfragen-Entit&auml;t in der Datenbank mit dem angemeldeten Nutzer als Initiator
     * und vorgeschlagenen Nutzer als Partner besteht. Wenn ja, gibt die Methode ein true zur&uuml;ck.
     * Falls eine {@code NullPointerException} fliegt, wird ein false zur&uuml;ckgegeben.
     *
     * @param   tempNutzer Stellt den vorgeschlagenen Nutzer dar.
     * @return  Gibt true zur&uuml;ck, wenn bereits eine Matchanfragen-Entit&auml;t mit dem angemeldeten und
     *          vorgeschlagenen Nutzer in der Datenbank existiert und false, wenn eine NullPointerException fliegt.
     */
    public boolean matchanfragenAlreadyExist(Nutzer tempNutzer) {
        try {
            Matchanfragen matchanfragen = dao.findMatchanfragenByInitiatorPartnerSpracheID(nutzer, tempNutzer, suchanfrage.getParamSpracheID());
            return matchanfragen.getId().getInitiator() == nutzer.getId()
                    && matchanfragen.getId().getPartner() == tempNutzer.getId()
                    && matchanfragen.getId().getSpracheID() == suchanfrage.getParamSpracheID();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Instanziiert und initialisiert ein {@code Matchanfragen}-Objekt und merged es in die Datenbank,
     * falls diese noch nicht in der Matchanfragen-Tabelle der Datenbank enthalten ist // todo CCE : if-Anweisung noch notwendig, da Button disabled, wenn Matchanfrage existiert?
     *
     * @param partnerID Stellt den Wert f&uuml;r die partner-Spalte in der Datenbank dar.
     */
    public void sendRequest(int partnerID) {
        Matchanfragen matchanfragen = new Matchanfragen();
        matchanfragen.getId().setInitiator(nutzer.getId());
        matchanfragen.getId().setPartner(partnerID);
        matchanfragen.setAngenommen((byte) 0);
        matchanfragen.getId().setSpracheID(suchanfrage.getParamSpracheID());
            dao.merge(matchanfragen);
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
     * Instanziiert {@link #suchergebnisseArrayList suchergebnisseArrayList} und ruft
     * {@link #calculateSuchanfrage() calculateSuchanfrage} auf, bevor {@link #suchergebnisseArrayList suchergebnisseArrayList}
     * mit den vorgeschlagenen Nutzern wiedergegeben wird.
     *
     * @return Eine {@code ArrayList} mit den vorgeschlagenen Nutzern f&uuml;r die Suchanfrage des angemeldeten Nutzers
     */
    public ArrayList<SuchergebnisModel> getSuchergebnisseArrayList() {
        suchergebnisseArrayList = new ArrayList<>();
        calculateSuchanfrage();
        return suchergebnisseArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    /**
     * Holt sich das {@code Suchanfrage}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der {@code SuchanfrageManagedBean} solange existiert, wie
     * die Session l&auml;uft. Anschließend wird das {@code Suchanfrage}-Objekt der {@code SuchanfrageManagedBean} dem {@link #suchanfrage suchanfrage} zugewiesen.
     */
    private void initSuchanfrage() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        suchanfrage = loggedNutzer.getSuchanfrage();
    }

    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der {@code SuchanfrageManagedBean} solange existiert, wie
     * die Session l&auml;uft. Anschließend wird das {@code Nutzer}-Objekt der {@code SuchanfrageManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        nutzer = loggedNutzer.getNutzer();
    }

    /**
     * Holt sich alle Nutzer aus der Datenbank, die der Sprache-ID aus der Suchanfrage entsprechen und h&auml;lt sie
     * sich in einer {@code ArrayList}. Dann wird f&uuml;r jeden einzelnen vorgeschlagenen Nutzer aus der Liste
     * gepr&uuml;ft, ob es sich nicht um den angemeledeten Nutzer handelt, ob das Geschlecht dem Geschlecht aus
     * der Suchanfrage und das Alter dem Alter aus der Suchanfrage entspricht. Wenn die Kriterien erf&uumlhlt sind,
     * werden die gemeinsamen Freizeitaktivit&auml;ten des angemeldeten und des vorgeschlagenen Nutzers erechnet
     * und in ein {@code String}-Objekt geschrieben.
     * Anschlie&szlig;end wird ein neues {@code SuchergebnisModel} erzeugt und dem
     * {@link #suchergebnisseArrayList suchergebnisseArrayList} hinzugef&uuml;gt.
     */
    private void calculateSuchanfrage() {
        ArrayList<Nutzer> nutzerMatchSprache = dao.findNutzerBySpracheID(suchanfrage.getParamSpracheID());

        for (Nutzer tempNutzer : nutzerMatchSprache) {
            if (! tempNutzer.equals(this.nutzer)
                    && tempNutzer.getGeschlecht().isSameGeschlecht(suchanfrage.getParamGeschlecht())
                    && isAlterInRange(suchanfrage, tempNutzer) ) {
                Set<Freizeitaktivitaeten> aktivitaeten = new HashSet<>(tempNutzer.getFreizeitaktivitaetenSet());
                aktivitaeten.retainAll(this.nutzer.getFreizeitaktivitaetenSet());
                if (!aktivitaeten.isEmpty()) {
                    List<String> aktivitaetenList = aktivitaeten.stream().map(Freizeitaktivitaeten::toString).collect(Collectors.toList());
                    String aktivitaetenString = String.join(",", aktivitaetenList);
                    suchergebnisseArrayList.add(new SuchergebnisModel(tempNutzer, aktivitaeten.size(), aktivitaetenString));
                }
            }
        }
    }

    /**
     * Pr&uuml;ft, ob das Alter des {@code Nutzer}-Objekts im Bereich des Alters aus dem {@code Suchanfrage}-Objekt liegt.
     * Wenn ja, gibt die Methode ein true zur&uuml;ck, andernfalls ein false.
     *
     * @param   suchanfrage Das {@code Suchanfrage}-Objekt, welches die Parameter f&uuml;r den Bereich des Alters enth&auml;lt.
     * @param   nutzer Das {@code Nutzer}-Objekt, welches das zu pr&uuml;fende Alter enth&auml;lt.
     * @return  Gibt true zur&uuml;ck, wenn das Alter im validen Bereich liegt und false, wenn das Alter au&szlig;erhalb
     *          dieses Bereichs liegt.
     */
    private boolean isAlterInRange(Suchanfrage suchanfrage, Nutzer nutzer) {
        int age = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        return suchanfrage.getParamAlterMin() <= age && suchanfrage.getParamAlterMax() >= age;
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
