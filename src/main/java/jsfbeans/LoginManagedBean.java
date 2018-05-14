package jsfbeans;

import dao.NutzerDAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    // TODO Joe: 14.05.2018 public oder auch mit Getter und Setter moeglich?
    public Nutzer nutzer = new Nutzer();
    private String mail;
    private String password;

    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    public String login() {
        if (validateNutzer(mail, password)) {
            nutzer = nutzerDAO.findNutzerByMail(mail);
            return "home";
        }
        return "login";
    }

    private boolean validateNutzer(String mail, String password) {
        Nutzer n = nutzerDAO.findNutzerByMail(mail);
        return n.getPasswort().equals(password);
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
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
