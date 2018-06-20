package jsfbeans;

import dao.DAO;
import models.Freizeitaktivitaeten;
import models.Matchanfragen;
import models.MatchanfragenModel;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Die {@code HomeManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r
 * die {@code home.xhtml}.
 */
@ManagedBean
@ViewScoped
public class HomeManagedBean {

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
     * Die {@code ArrayList} mit der Typisierung {@code MatchanfragenModel} enth&auml;lt alle offenen Matchanfragen.
     */
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code HomeManagedBean}-Objekt und ruft dabei die Methode {@link #initNutzer() initNutzer} auf.
     */
    public HomeManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    /**
     * Gibt Anhand einer Sprach-ID den Sprachennamen als {@code String} zur&uuml;ck.
     * Diese Methode wird dazu genutzt, um in der Tabelle der {@code home.xhtml} die Sprachennamen anzuzeigen.
     *
     * @param   spracheID Sprach-ID des gesuchten Sprachennamens
     * @return  Gibt den Namen der gesuchten Sprache zur&uuml;ck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
    }

    /**
     * Setzt den angenommen-Parameter der {@code Matchanfragen} auf 1, wodurch die Matchanfrage in der Datenbank
     * als angenommen gekennzeichnet wird.
     *
     * @param matchanfragen Die Matchanfrage, die als "angenommen" gekennzeichnet werden soll.
     */
    public void acceptMatchanfrage(Matchanfragen matchanfragen) {
        matchanfragen.setAngenommen((byte) 1);
        dao.merge(matchanfragen);
    }

    /**
     * L&ouml;scht eine abgelehnte {@code Matchanfragen} aus der Datenbank.
     *
     * @param matchanfragen     Die Matchanfrage, die abgelehnt wurde.
     */
    public void refuseMatchanfrage(Matchanfragen matchanfragen) {
        dao.deleteMatchanfrage(matchanfragen);
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
     * Instanziiert {@link #matchanfragenModelArrayList matchanfragenModelArrayList} und ruft
     * {@link #calculateMatchanfragen() calculateMatchanfragen} auf, bevor {@link #matchanfragenModelArrayList matchanfragenModelArrayList}
     * mit den offenen Matchanfragen wiedergegeben wird.
     *
     * @return  Eine {@code ArrayList} mit den offenen Matchanfragen des angemeldeten Nutzers
     */
    public ArrayList<MatchanfragenModel> getMatchanfragenModelArrayList() {
        matchanfragenModelArrayList = new ArrayList<>();
        calculateMatchanfragen();
        return matchanfragenModelArrayList;
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
     * Holt sich alle offenen Matchanfragen, die der angemeldete Nutzer hat und h&auml;lt sie sich in einer {@code ArrayList}.
     * Dann werden die {@code Freizeitaktivitaeten} der {@code Nutzer}, welche die Matchanfragen gesendet haben (also die Initiatoren),
     * mit den {@code Freizeitaktivitaeten} des angemeldeten Nutzers verglichen und gemeinsame {@code Freizeitaktivitaeten} werden
     * in einen {@code String} geschrieben.
     * Anschlie&szlig;end wird ein neues {@code MatchanfragenModel} erzeugt und dem {@link #matchanfragenModelArrayList matchanfragenModelArrayList} hinzugef&uuml;gt.
     */
    private void calculateMatchanfragen() {
        ArrayList<Matchanfragen> openMatchanfragen = dao.findMatchanfragenByNutzerID(nutzer.getId());

        for (Matchanfragen anOpenMatchanfragen : openMatchanfragen) {
            Nutzer aNutzer = dao.findNutzerByID(anOpenMatchanfragen.getId().getInitiator());
            Set<Freizeitaktivitaeten> aktivitaeten = new HashSet<>(aNutzer.getFreizeitaktivitaetenSet());
            aktivitaeten.retainAll(this.nutzer.getFreizeitaktivitaetenSet());
            if (!aktivitaeten.isEmpty()) {
                List<String> aktivitaetenList = aktivitaeten.stream().map(Freizeitaktivitaeten::toString).collect(Collectors.toList());
                String aktivitaetenString = String.join(", ", aktivitaetenList);
                matchanfragenModelArrayList.add(new MatchanfragenModel(aNutzer, aktivitaeten.size(), aktivitaetenString, anOpenMatchanfragen));
            }
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
