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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Die Managed Bean für die "home.xhtml".
 */
@ManagedBean
@ViewScoped
public class HomeManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO                             dao;
    private Nutzer                          nutzer;
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;
    private boolean                         active;

    // ============================  Constructors  ===========================79

    /**
     * Im Konstruktor wird die Methode initNutzer() aufgerufen.
     */
    public HomeManagedBean() {
        initNutzer();
        this.active = true;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        ArrayList<Matchanfragen> openMatchanfragen = dao.findMatchanfragenByID(nutzer.getId());
        for (Matchanfragen anOpenMatchanfragen : openMatchanfragen) {
            Nutzer aNutzer = dao.findNutzerByID(anOpenMatchanfragen.getInitiator());
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
