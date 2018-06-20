package jsfbeans;

import dao.DAO;
import models.*;

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
 * Die {@code MatchesManagedBean} dient zur Verwaltung der Variablen und Methoden f&uuml;r die {@code matches.xhtml}.
 */
@ManagedBean
@ViewScoped
public class MatchesManagedBean {

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
     * Die {@code ArrayList} mit der Typisierung {@code MatchanfragenModel} enth&auml;lt alle "angenommenen" {@code Matchanfragen}.
     */
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79
    /**
     * Initialisiert ein neu erzeugtes {@code MatchesManagedBean}-Objekt und ruft dabei die Methode {@link #initNutzer() initNutzer} auf.
     */
    public MatchesManagedBean() {
        initNutzer();
    }

    public MatchesManagedBean(DAO dao, Nutzer nutzer) {
        this.dao = dao;
        this.nutzer = nutzer;
    }

    // ===========================  public  Methods  =========================79
    /**
     * Gibt Anhand einer Sprach-ID den Sprachennamen als {@code String} zur&uuml;ck.
     * Diese Methode wird dazu genutzt, um in der Tabelle der {@code matches.xhtml} die Sprachennamen anzuzeigen.
     *
     * @param spracheID Sprach-ID des gesuchten Sprachennamens
     * @return Gibt den Namen der gesuchten Sprache zur&uuml;ck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
    }

    /**
     * L&ouml;scht eine Matchanfrage aus der Datenbank.
     *
     * @param matchanfragen Die Matchanfrage, die gel&ouml;scht werden soll.
     */
    public void deleteMatchanfrage(Matchanfragen matchanfragen) {
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
     * {@link #calculateAnsweredMatchanfragen() calculateAnsweredMatchanfragen} auf, bevor {@link #matchanfragenModelArrayList matchanfragenModelArrayList}
     * mit den zustandegekommenen {@code Matchanfragen} wiedergegeben wird.
     *
     * @return Eine {@code ArrayList} mit den zustandegekommenen Matches des aktuell angemeldeten Nutzers
     */
    public ArrayList<MatchanfragenModel> getMatchanfragenModelArrayList() {
        matchanfragenModelArrayList = new ArrayList<>();
        calculateAnsweredMatchanfragen();
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
     * Es folgt eine Differenzierung zwischen dem, der die Matchanfrage gesendet hat (Initiator) und dem, der sie empfangen hat (Partner).
     * Dann werden die gemeinsamen {@code Freizeitaktivitaeten} des Initiators und Partners ermittelt und in einen {@code String} geschrieben.
     * Anschlie&szlig;end wird ein neues {@code MatchanfragenModel} erzeugt und dem {@link #matchanfragenModelArrayList matchanfragenModelArrayList} hinzugef&uuml;gt.
     * Falls {@code Matchanfragen} existieren, deren Initiator und Partner keine gemeinsamen {@code Freizeitaktivitaeten} vorweisen, werden diese gel&ouml;scht.
     */
    private void calculateAnsweredMatchanfragen() {
        ArrayList<Matchanfragen> acceptedMatchanfragen = dao.findMatchanfragenByAllColumns(nutzer.getId());
        for (Matchanfragen anAcceptedMatchanfragen : acceptedMatchanfragen) {
            Nutzer aNutzer;
            String origin;
            if (nutzer.equals(dao.findNutzerByID(anAcceptedMatchanfragen.getId().getInitiator()))) {
                aNutzer = dao.findNutzerByID(anAcceptedMatchanfragen.getId().getPartner());
                origin = "Gesendet";
            } else {
                aNutzer = dao.findNutzerByID(anAcceptedMatchanfragen.getId().getInitiator());
                origin = "Empfangen";
            }
            Set<Freizeitaktivitaeten> aktivitaeten = new HashSet<>(aNutzer.getFreizeitaktivitaetenSet());
            aktivitaeten.retainAll(this.nutzer.getFreizeitaktivitaetenSet());
            if (!aktivitaeten.isEmpty()) {
                List<String> aktivitaetenList = aktivitaeten.stream().map(Freizeitaktivitaeten::toString).collect(Collectors.toList());
                String aktivitaetenString = String.join(", ", aktivitaetenList);
                matchanfragenModelArrayList.add(new MatchanfragenModel(aNutzer, aktivitaeten.size(), aktivitaetenString, anAcceptedMatchanfragen, origin));
            } else {
                dao.deleteMatchanfrage(anAcceptedMatchanfragen);
            }
        }
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
