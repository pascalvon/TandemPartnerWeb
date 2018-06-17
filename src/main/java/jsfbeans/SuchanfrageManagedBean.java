package jsfbeans;

import dao.DAO;
import models.Nutzer;
import models.Suchanfrage;
import utilities.FreizeitaktivitaetenStringTransformer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class SuchanfrageManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO                     dao;
    private Nutzer                  nutzer;
    private String                  selectedFreizeitaktivitaetenString;
    private Suchanfrage             suchanfrage;
    private ArrayList<Suchanfrage>  suchanfrageArrayList;

    // ============================  Constructors  ===========================79
    public SuchanfrageManagedBean() {
        this.nutzer         = initNutzer();
        this.suchanfrage    = new Suchanfrage();
    }

    // ===========================  public  Methods  =========================79
    public String search() {
        if (!validateSuchanfrage() && dao.findSuchanfrageByNutzer(nutzer).size()<5) {
            suchanfrage.addNutzer(nutzer);
            dao.merge(suchanfrage);
        }
            return "suchergebnisse?faces-redirect=true";
    }

    public void deleteSuchanfrage(Suchanfrage savedSuchanfrage) {
        dao.deleteSuchanfrageNQ(savedSuchanfrage);
    }

    public String useSuchanfrage(Suchanfrage savedSuchanfrage) {
        this.suchanfrage = savedSuchanfrage;
        return "suchergebnisse?faces-redirect=true";
    }

    public String showSpracheName(int spracheID) {
        return dao.findSpracheByID(String.valueOf(spracheID)).getNameSprache();
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        selectedFreizeitaktivitaetenString = FreizeitaktivitaetenStringTransformer.selectedFreizeitaktivitaetenString(nutzer);
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {

        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    public Suchanfrage getSuchanfrage() {
        return suchanfrage;
    }

    public void setSuchanfrage(Suchanfrage suchanfrage) {
        this.suchanfrage = suchanfrage;
    }

    public ArrayList<Suchanfrage> getSuchanfrageArrayList() {
        suchanfrageArrayList = new ArrayList<>();
        suchanfrageArrayList = dao.findSuchanfrageByNutzer(nutzer);
        return this.suchanfrageArrayList;
    }

    public void setSuchanfrageArrayList(ArrayList<Suchanfrage> suchanfrageArrayList) {
        this.suchanfrageArrayList = suchanfrageArrayList;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
        return nutzer;
    }

    private boolean validateSuchanfrage() {
        try {
            Suchanfrage existingSuchanfrage = dao.findSuchanfrage(suchanfrage, nutzer);
            return existingSuchanfrage.getParamSpracheID() == suchanfrage.getParamSpracheID() &&
                    existingSuchanfrage.getNutzer().getId() == nutzer.getId();
        } catch (NullPointerException e) {
            return false;
        }
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
