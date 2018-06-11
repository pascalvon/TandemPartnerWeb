package validators;


import dao.DAO;
import models.Nutzer;
import utilities.HashedPasswordGenerator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@RequestScoped
public class LoginValidator implements Validator {

    @EJB
    private DAO dao;
    public Nutzer nutzer = new Nutzer();
    private String mail;
    private String password;

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException
    {
        mail = (String) o;
        password = (String) uiComponent.getAttributes().get("password");

        if (mail.isEmpty() && password.isEmpty()){
            return;
        }
        else if(!validateNutzer(mail,password))
        {
            throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
        }
        return;
    }

    private boolean validateNutzer(String mail, String password) {
        try {
            nutzer = dao.findNutzerByMail(mail);
            return HashedPasswordGenerator.generateHash(password).equals(nutzer.getPasswort());
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
}
