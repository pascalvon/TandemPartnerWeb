package jsfbeans;

import dao.NutzerDAO;
import models.Matchanfragen;
import models.MatchanfragenModel;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

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
        nutzer = initNutzer();
    }

    // ===========================  public  Methods  =========================79

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public ArrayList<MatchanfragenModel> getMatchanfragenModelArrayList() {
        // TODO Joe: 25.05.2018 Sollte die Logik enthalten, um gemeinsam Freizeitaktivitaeten auszugeben
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
        ArrayList<Matchanfragen> openMatchanfragen = nutzerDAO.findMatchanfragenByMail(nutzer.getMail(), 0);
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
