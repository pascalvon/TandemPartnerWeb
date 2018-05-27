package jsfbeans;

import dao.NutzerDAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    @EJB
    private NutzerDAO nutzerDAO;
    public Nutzer nutzer = new Nutzer();
    private String mail;
    private String password;

    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    // TODO Joe: 01.05.2018 Exception fuer den Fall, dass falsche E-Mail angegeben wird, muss noch gefangen werden. (27.05.18 Wird bereits gefangen, aber Validator fehlt noch)
    // TODO Joe: 01.05.2018 Validator auslagern auf eigene Klasse und Text ausgeben bei falscher Eingabe (siehe TODO zuvor).
    public String login() {
        if (validateNutzer(mail, password)) {
            nutzer = nutzerDAO.findNutzerByMail(mail);
            return "home";
        }
        return "login";
    }

    private boolean validateNutzer(String mail, String password) {
        try {
            Nutzer n = nutzerDAO.findNutzerByMail(mail);
            return n.getPasswort().equals(password);
        } catch (NullPointerException e) {
            return false;
        }
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

    public String logout()  {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
