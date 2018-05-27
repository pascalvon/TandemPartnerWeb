package jsfbeans;

import dao.NutzerDAO;
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

@ManagedBean
@ViewScoped
public class HomeManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer;
    private ArrayList<MatchanfragenModel> matchanfragenModelArrayList;

    // ============================  Constructors  ===========================79

    public HomeManagedBean() {
        this.nutzer = initNutzer();
    }

    // ===========================  public  Methods  =========================79

    public void acceptMatchanfrage(Matchanfragen matchanfragen) {
        matchanfragen.setAngenommen((byte) 1);
        nutzerDAO.merge(matchanfragen);
    }

    public void refuseMatchanfrage(Matchanfragen matchanfragen) {
        nutzerDAO.deleteMatchanfrage(matchanfragen);
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
    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
        return nutzer;
    }

    private void calculateMatchanfragen() {
        ArrayList<Matchanfragen> openMatchanfragen = nutzerDAO.findMatchanfragenByMail(nutzer.getMail());
        ArrayList<Nutzer> nutzerFromOpenMatchanfragen = new ArrayList<>();

        for (Matchanfragen anOpenMatchanfragen : openMatchanfragen) {
            Nutzer aNutzer = nutzerDAO.findNutzerByMail(anOpenMatchanfragen.getInitiator());
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
