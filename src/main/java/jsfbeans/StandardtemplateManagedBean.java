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
public class StandardtemplateManagedBean {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO dao;
    private Nutzer nutzer;
    private String logStatus;

    // ============================  Constructors  ===========================79
    public StandardtemplateManagedBean() {
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

    /**
     * Die Session wird ung&uuml;ltig gemacht und alle an sie gebundenen Objekte werden aufgehoben.
     *
     * @return Gibt den {@code String} zur&uuml;ck, mit dem der Nutzer auf die {@code login.xhtml} weitergeleitet wird.
     */
    public String logout()  {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public String getLogStatus() {
        if (validateNutzer(nutzer.getMail())) {
            logStatus = "Login";
        } else {
            logStatus = "Logout";
        }
        return logStatus;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
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
