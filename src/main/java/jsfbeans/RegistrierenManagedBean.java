package jsfbeans;

import dao.DAO;
import models.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class RegistrierenManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO                             dao;
    private Nutzer                          nutzer;
    private int                             bezirkID;
    private ArrayList<Sprache>              allSprachenList;
    private ArrayList<SelectItem>           selectItemList;
    private List<Sprache>                   selectedSprachenList;
    private String                          selectedSprachenString;
    private ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList;
    private String                          selectedFreizeitaktivitaetenString;

    // ============================  Constructors  ===========================79
    public RegistrierenManagedBean() {
        this.nutzer                             = new Nutzer();
        this.selectedSprachenList               = new ArrayList<>();
        this.selectedFreizeitaktivitaetenList   = new ArrayList<>();
    }

    // ===========================  public  Methods  =========================79

    @PostConstruct
    public void initialise() {
        allSprachenList = new ArrayList<>();
        allSprachenList = dao.findSprachen();
    }

    public String register() {
            nutzer.addBezirk(dao.findBezirkByID(bezirkID));
            addSprachenToNutzer();
            addFreizeitaktivitaetenToNutzer();
            dao.merge(nutzer);
            initNutzer();
            return "home";
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public int getBezirkID() {
        return bezirkID;
    }

    public void setBezirkID(int bezirkID) {
        this.bezirkID = bezirkID;
    }

    public ArrayList<Sprache> getAllSprachenList() {
        return allSprachenList;
    }

    public void setAllSprachenList(ArrayList<Sprache> allSprachenList) {
        this.allSprachenList = allSprachenList;
    }

    public ArrayList<SelectItem> getSelectItemList() {
        selectItemList = new ArrayList<>();
        selectItemList.add(new SelectItem(1, "Englisch"));
        selectItemList.add(new SelectItem(2, "Deutsch"));
        selectItemList.add(new SelectItem(3, "Französisch"));
        selectItemList.add(new SelectItem(4, "Hindi"));
        selectItemList.add(new SelectItem(5, "Italienisch"));
//        for (Sprache sprachen : getAllSprachenList()) {
//            selectItemList.add(new SelectItem(sprachen.getId(), sprachen.getNameSprache()));
//        }
        return selectItemList;
    }

    public void setSelectItemList(ArrayList<SelectItem> selectItemList) {
        this.selectItemList = selectItemList;
    }

    public String getSelectedSprachenString() {
        return selectedSprachenString;
    }

    public void setSelectedSprachenString(String selectedSprachenString) {
        this.selectedSprachenString = selectedSprachenString;
    }

    public String getSelectedFreizeitaktivitaetenString() {
        return selectedFreizeitaktivitaetenString;
    }

    public void setSelectedFreizeitaktivitaetenString(String selectedFreizeitaktivitaetenString) {
        this.selectedFreizeitaktivitaetenString = selectedFreizeitaktivitaetenString;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void addSprachenToNutzer() {
        String[] selectedSprachenArray = selectedSprachenString.split(",");
        for (String aSelectedSprachenArray : selectedSprachenArray) {
            selectedSprachenList.add(dao.findSpracheByID(aSelectedSprachenArray));
        }
        for (Sprache aSelectedSprachenList : selectedSprachenList) {
            nutzer.addSprache(aSelectedSprachenList);
        }
    }

    private void addFreizeitaktivitaetenToNutzer() {
        String[] selectedFreizeitaktivitaetenArray = selectedFreizeitaktivitaetenString.split(",");
        for (String aSelectedFreizeitaktivitaetenArray : selectedFreizeitaktivitaetenArray) {
            selectedFreizeitaktivitaetenList.add(dao.findFreizeitaktivitaetenByID(aSelectedFreizeitaktivitaetenArray));
        }
        for (Freizeitaktivitaeten aSelectedFrezeitaktivitaetenList : selectedFreizeitaktivitaetenList) {
            nutzer.addFreizeitaktivitaeten(aSelectedFrezeitaktivitaetenList);
        }
    }

    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        loginManagedBean.nutzer = dao.findNutzerByMail(nutzer.getMail());
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
