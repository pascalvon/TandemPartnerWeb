package jsfbeans;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ImpressumManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO dao;
    private Nutzer nutzer;
    private String logStatus;

    // ============================  Constructors  ===========================79
    public ImpressumManagedBean() {
        initNutzer();
    }

    // ===========================  public  Methods  =========================79
    public String redirectToHome() {
        if (validateNutzer(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "home?faces-redirect=true";
        }
    }

    public String redirectToProfil() {
        if (validateNutzer(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "profil?faces-redirect=true";
        }
    }

    public String redirectToSuchanfragen() {
        if (validateNutzer(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "suchanfrage?faces-redirect=true";
        }
    }

    public String redirectToMatches() {
        if (validateNutzer(nutzer.getMail())) {
            return "login?faces-redirect=true";
        } else {
            return "matches?faces-redirect=true";
        }
    }

    public String getLogStatus() {
        if (validateNutzer(nutzer.getMail())) {
            logStatus = "Login";
        } else {
            logStatus = "Logout";
        }
        return logStatus;
    }

    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }
    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
    }

    private boolean validateNutzer(String mail) {
        try {
            Nutzer n = dao.findNutzerByMail(mail);
            return !n.getMail().equals(nutzer.getMail());
        } catch (NullPointerException e) {
            return true;
        }
    }
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
