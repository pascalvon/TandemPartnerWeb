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

@ManagedBean
@ViewScoped
public class SuchergebnisseManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO                             dao;
    private Suchanfrage                     suchanfrage;
    private Nutzer                          nutzer;
    private ArrayList<SuchergebnisModel>    suchergebnisseArrayList;

    // ============================  Constructors  ===========================79
    public SuchergebnisseManagedBean() {
        initSuchanfrage();
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(spracheID).getNameSprache();
    }

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

    public void sendRequest(int partnerID) {
        Matchanfragen matchanfragen = new Matchanfragen();
        matchanfragen.getId().setInitiator(nutzer.getId());
        matchanfragen.getId().setPartner(partnerID);
        matchanfragen.setAngenommen((byte) 0);
        matchanfragen.getId().setSpracheID(suchanfrage.getParamSpracheID());
        if (!validateMatchanfragen(matchanfragen)) {
            dao.merge(matchanfragen);
        }
    }

    public Suchanfrage getSuchanfrage() {
        return suchanfrage;
    }

    public ArrayList<SuchergebnisModel> getSuchergebnisseArrayList() {
        suchergebnisseArrayList = new ArrayList<>();
        calculateSuchanfrage();
        return suchergebnisseArrayList;
    }

    public void setSuchergebnisseArrayList(ArrayList<SuchergebnisModel> suchergebnisseArrayList) {
        this.suchergebnisseArrayList = suchergebnisseArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void initSuchanfrage() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        suchanfrage = loggedNutzer.getSuchanfrage();
    }

    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        nutzer = loggedNutzer.getNutzer();
    }

    private boolean validateMatchanfragen(Matchanfragen matchanfragen) {
        try {
            Matchanfragen matchanfragen1 = dao.findMatchanfragenByMatchanfragen(matchanfragen);
            return matchanfragen.equals(matchanfragen1);
        } catch(NullPointerException e) {
            return false;
        }
    }

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

    private boolean isAlterInRange(Suchanfrage suchanfrage, Nutzer nutzer) {
        int age = AgeCalculator.calculateAge(nutzer.getGeburtsdatum());
        return suchanfrage.getParamAlterMin() <= age && suchanfrage.getParamAlterMax() >= age;
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
