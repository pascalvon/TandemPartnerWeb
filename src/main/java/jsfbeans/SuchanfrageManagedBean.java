package jsfbeans;

import dao.NutzerDAO;
import models.Freizeitaktivitaeten;
import models.Nutzer;
import models.Suchanfrage;

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
    private NutzerDAO nutzerDAO;
    private Nutzer nutzer;
    private String selectedFreizeitaktivitaetenString;
    private Suchanfrage suchanfrage = new Suchanfrage();

    // ============================  Constructors  ===========================79
    public SuchanfrageManagedBean() {
        this.nutzer = initNutzer();
    }

    // ===========================  public  Methods  =========================79
    public String search() {
        suchanfrage.setNutzer(nutzer);
        nutzerDAO.merge(suchanfrage);
        return "suchergebnisse";
    }
    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    // TODO Joe: 16.05.2018 Utility-Klasse erstellen
    public String getSelectedFreizeitaktivitaetenString() {
        ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>(nutzer.getFreizeitaktivitaetenSet());
        String[] selectedFreizeitaktivitaetenArray = new String[selectedFreizeitaktivitaetenList.size()];
        for (int i = 0; i < selectedFreizeitaktivitaetenList.size(); i++) {
            selectedFreizeitaktivitaetenArray[i] = String.valueOf(selectedFreizeitaktivitaetenList.get(i).getId());
        }
        selectedFreizeitaktivitaetenString = String.join(",", selectedFreizeitaktivitaetenArray);
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

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // TODO Joe: 16.05.2018 Zum Nutzer aus home aendern
    private Nutzer initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loggedNutzer = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loggedNutzer.nutzer;
        return nutzer;
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
