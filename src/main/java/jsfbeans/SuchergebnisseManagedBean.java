package jsfbeans;

import dao.NutzerDAO;
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
// TODO Joe: 16.05.2018 ArrayList von mir selbst halten und mit den Suchergebnissen fuellen, die der Suchanfrage entsprechen
    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79

    @EJB
    private NutzerDAO nutzerDAO;
    private Suchanfrage suchanfrage;
    private Nutzer nutzer;
    private ArrayList<Suchergebnisse> suchergebnisseArrayList = new ArrayList<>();

//    {
//        suchergebnisseArrayList.add(new Suchergebnisse(initNutzer()));
//    }

    // ============================  Constructors  ===========================79
    public SuchergebnisseManagedBean() {
        this.suchanfrage = initSuchanfrage();
        this.nutzer = initNutzer();
    }
    // ===========================  public  Methods  =========================79


    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public ArrayList<Suchergebnisse> getSuchergebnisseArrayList() {
        calculateSuchanfrage();
        return suchergebnisseArrayList;
    }

    public void setSuchergebnisseArrayList(ArrayList<Suchergebnisse> suchergebnisseArrayList) {
        this.suchergebnisseArrayList = suchergebnisseArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private Suchanfrage initSuchanfrage() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        suchanfrage = loggedNutzer.getSuchanfrage();
        return suchanfrage;
    }

    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SuchanfrageManagedBean loggedNutzer = (SuchanfrageManagedBean) elContext.getELResolver().getValue(elContext, null, "suchanfrageManagedBean");
        nutzer = loggedNutzer.getNutzer();
        return nutzer;
    }

    private void calculateSuchanfrage() {
        ArrayList<Nutzer> nutzerMatchSprache = nutzerDAO.findNutzerBySpracheID(suchanfrage.getParamSpracheID());

        for (Nutzer tempNutzer : nutzerMatchSprache) {
            if (! tempNutzer.equals(this.nutzer) && tempNutzer.getGeschlecht().isSameGeschlecht(suchanfrage.getParamGeschlecht()) && isAlterInRange(suchanfrage, tempNutzer)) {
                Set<Freizeitaktivitaeten> aktivitaeten = new HashSet<>(tempNutzer.getFreizeitaktivitaetenSet());
                aktivitaeten.retainAll(this.nutzer.getFreizeitaktivitaetenSet());
                if (!aktivitaeten.isEmpty()) {
                    List<String> aktivitaetenListe = aktivitaeten.stream().map(Freizeitaktivitaeten::toString).collect(Collectors.toList());
                    String aktivitaetenString = String.join(",", aktivitaetenListe);
                    suchergebnisseArrayList.add(new Suchergebnisse(tempNutzer, aktivitaeten.size(), aktivitaetenString));
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
