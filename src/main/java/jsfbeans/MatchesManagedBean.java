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

@ManagedBean
@ViewScoped
public class MatchesManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO                             dao;
    private Nutzer                          nutzer;
    private ArrayList<MatchanfragenModel>   matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79
    public MatchesManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(String.valueOf(spracheID)).getNameSprache();
    }

    public void deleteMatchanfrage(Matchanfragen matchanfragen) {
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
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
    }

    private void calculateMatchanfragen() {
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
                String aktivitaetenString = String.join(",", aktivitaetenList);
                matchanfragenModelArrayList.add(new MatchanfragenModel(aNutzer, aktivitaetenString, aktivitaeten.size(), anAcceptedMatchanfragen, origin));
            } else {
                // TODO Joe: 27.05.2018 Soll Matchanfrage geloescht werden, wenn es keine gemeinsamen Aktivitaeten gibt? Weitere Moeglichkeit ist bei erneutem bearbeiten der Aktiviaeten,
                // TODO Joe: 27.05.2018 dass Matchanfrage wieder angezeigt wird. Aber dadurch kann auf dauer die DB zugemuellt werden.
                dao.deleteMatchanfrage(anAcceptedMatchanfragen);
            }
        }
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
