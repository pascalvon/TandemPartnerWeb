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
 * Die {@code HomeManagedBean} dient zur Verwaltung der Variablen und Methoden fuer
 * die {@code home.xhtml}.
 */
@ManagedBean
@ViewScoped
public class HomeManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    /**
     * Das DAO-Objekt, welches Methoden enthaelt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO                             dao;

    /**
     * Das Nutzer-Objekt, welches den aktuell angemeldeten Nutzer darstellt, der im Konstruktor
     * durch die Methode {@link #initNutzer() (void) initNutzer} initialisiert wird.
     */
    private Nutzer                          nutzer;

    /**
     * Die {@code ArrayList} mit der Typisierung {@code MatchanfragenModel} enthaelt alle offenen Matchanfragen.
     */
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79

    /**
     * Einziger Konstruktor der Klasse, in der die Methode {@link #initNutzer() (void)} aufgerufen wird.
     */
    public HomeManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79

    /**
     * Gibt Anhand einer SprachID den Sprachennamen als {@code String} zurueck.
     * Diese Methode wird dazu genutzt, um in der Tabelle der {@code home.xhtml} die Sprachennamen anzuzeigen.
     *
     * @param   spracheID SprachID des gesuchten Sprachennamens
     * @return  Gibt den Namen der gesuchten Sprache zurueck.
     */
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(String.valueOf(spracheID)).getNameSprache();
    }

    /**
     * Setzt den angenommen-Parameter der {@code Matchanfragen} auf 1, wodurch die Matchanfrage in der Datenbank
     * als angenommen gekennzeichnet wird.
     *
     * @param matchanfragen     Die Matchanfrage, die als "angenommen" gekennzeichnet werden soll.
     */
    public void acceptMatchanfrage(Matchanfragen matchanfragen) {
        matchanfragen.setAngenommen((byte) 1);
        dao.merge(matchanfragen);
    }

    /**
     * Loescht eine abgelehnte {@code Matchanfragen} aus der Datenbank.
     *
     * @param matchanfragen     Die Matchanfrage, die abgelehnt wurde.
     */
    public void refuseMatchanfrage(Matchanfragen matchanfragen) {
        dao.deleteMatchanfrage(matchanfragen);
    }

    /**
     * Liefert den {@code Nutzer} zurueck, der eingeloggt ist.
     *
     * @return  Das Nutzer-Objekt.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

//    /**
//     *
//     * @param nutzer
//     */
//    public void setNutzer(Nutzer nutzer) {
//        this.nutzer = nutzer;
//    }

    /**
     * Initialisiert {@link #matchanfragenModelArrayList (ArrayList)} und ruft
     * {@link #calculateMatchanfragen() (void)} auf, bevor {@link #matchanfragenModelArrayList (ArrayList)}
     * mit den offenen Matchanfragen wiedergegeben wird.
     *
     * @return  Eine {@code ArrayList} mit den offenen Matchanfragen des aktuell angemeldeten Nutzers
     */
    public ArrayList<MatchanfragenModel> getMatchanfragenModelArrayList() {
        matchanfragenModelArrayList = new ArrayList<>();
        calculateMatchanfragen();
        return matchanfragenModelArrayList;
    }

//    public void setMatchanfragenModelArrayList(ArrayList<MatchanfragenModel> matchanfragenModelArrayList) {
//        this.matchanfragenModelArrayList = matchanfragenModelArrayList;
//    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79

    /**
     * Initialisiert den eingeloggten Nutzer anhand der SessionScope der {@code LoginManagedBean},
     * um den aktuell eingeloggten Nutzer der {@code HomeManagedBean} zu uebergeben.
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
