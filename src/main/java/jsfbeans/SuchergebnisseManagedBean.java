package jsfbeans;

import dao.NutzerDAO;
import models.*;
import utilities.AgeCalculator;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
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
        validateSuchanfrage();
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

    private void validateSuchanfrage() {
        ArrayList<Nutzer> nutzerMatchSprache = nutzerDAO.findNutzerBySpracheID(suchanfrage.getParamSpracheID());
        for (Nutzer aNutzerMatchSprache : nutzerMatchSprache) {
            suchergebnisseArrayList.add(new Suchergebnisse(aNutzerMatchSprache));
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
