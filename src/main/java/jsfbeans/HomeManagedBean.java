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
 * Die HomeManagedBean dient zur Verwaltung der Variablen und Methoden fuer
 * die <i>home.xhtml</i>.
 */
@ManagedBean
@ViewScoped
public class HomeManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das DAO-Objekt enthaelt Methoden, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                             dao;

    /**
     * Das Nutzer-Objekt stellt den aktuell angemeldeten Nutzer dar, welcher im Konstruktor
     * durch die Methode <i>initNutzer()</i> initialisiert wird.
     */
    private Nutzer                          nutzer;

    /**
     * Die ArrayList mit der Typisierung MatchanfragenModel enthaelt alle offenen Matchanfragen.
     */
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79

    /**
     * Im Konstruktor wird die Methode initNutzer() aufgerufen.
     */
    public HomeManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79

    /**
     * Gibt Anhand einer SprachID den Sprachennamen als String zurueck
     * @param spracheID SprachID des gesuchten Sprachennamens
     * @return Gibt den Namen der gesuchten Sprache zurueck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(String.valueOf(spracheID)).getNameSprache();
    }

    /**
     * Setzt den angenommen-Parameter der Matchanfrage auf 1.
     * @param matchanfragen Die Matchanfrage, die als "angenommen" gekennzeichnet werden soll.
     */
    public void acceptMatchanfrage(Matchanfragen matchanfragen) {
        matchanfragen.setAngenommen((byte) 1);
        dao.merge(matchanfragen);
    }

    /**
     * Loescht eine abgelehnte Matchanfrage.
     * @param matchanfragen Die Matchanfrage, die abgelehnt wurde.
     */
    public void refuseMatchanfrage(Matchanfragen matchanfragen) {
        dao.deleteMatchanfrage(matchanfragen);
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public ArrayList<MatchanfragenModel> getMatchanfragenModelArrayList() {
        matchanfragenModelArrayList = new ArrayList<>();
        calculateMatchanfragen();
        return matchanfragenModelArrayList;
    }

    public void setMatchanfragenModelArrayList(ArrayList<MatchanfragenModel> matchanfragenModelArrayList) {
        this.matchanfragenModelArrayList = matchanfragenModelArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79

    /**
     * Initialisiert den eingeloggten Nutzer anhand der Session der LoginManagedBean.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
    }

    /**
     * Berechnet die Matchanfragen des eingeloggten Nutzers.
     */
    private void calculateMatchanfragen() {
        ArrayList<Matchanfragen> openMatchanfragen = dao.findMatchanfragenByNutzerID(nutzer.getId());
        for (Matchanfragen anOpenMatchanfragen : openMatchanfragen) {
            Nutzer aNutzer = dao.findNutzerByID(anOpenMatchanfragen.getId().getInitiator());
            Set<Freizeitaktivitaeten> aktivitaeten = new HashSet<>(aNutzer.getFreizeitaktivitaetenSet());
            aktivitaeten.retainAll(this.nutzer.getFreizeitaktivitaetenSet());
            if (!aktivitaeten.isEmpty()) {
                List<String> aktivitaetenList = aktivitaeten.stream().map(Freizeitaktivitaeten::toString).collect(Collectors.toList());
                String aktivitaetenString = String.join(",", aktivitaetenList);
                matchanfragenModelArrayList.add(new MatchanfragenModel(aNutzer, aktivitaetenString, aktivitaeten.size(), anOpenMatchanfragen));
            }
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
