package jsfbeans;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private DAO dao;
    public Nutzer nutzer = new Nutzer();
    private String mail;
    private String password;

    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79
    public String login() throws ValidatorException {
        nutzer = dao.findNutzerByMail(mail);
        return "home?faces-redirect=true";
    }


    public String logout()  {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    private boolean validateNutzer(String mail, String password) {
        try {
            Nutzer n = dao.findNutzerByMail(mail);
            return n.getPasswort().equals(password);
        } catch (NullPointerException e) {
            return false;
        }
    }

    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
