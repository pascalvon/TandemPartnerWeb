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

/**
 * Der Validator, welcher die vom Nutzer eingegebenen Login-Daten validiert.
 */
@ManagedBean
@RequestScoped
public class LoginValidator implements Validator {

    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO     dao;

    /**
     * {@code Nutzer}-Objekt, welches zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer  nutzer;

    /**
     * {@code String}, welcher die Eingabe der E-Mail-Adresse des Nutzers enth&auml;lt.
     */
    private String  mail;

    /**
     * {@code String}, welcher die Eingabe des Passworts des Nutzers enth&auml;lt.
     */
    private String  password;

    /**
     * Pr&uuml;ft, ob die Eingaben valide sind.
     *
     * @param facesContext Das {@code FacesContext}-Objekt, welches alle Statusinformationen der Anfrage enth&auml;lt.
     * @param uiComponent Das {@code UIComponent}-Objekt, welches die Basisklasse f&uuml;r alle
     *                    Oberfl&auml;chenkomponenten in JSF darstellt.
     * @param o Das Objekt, welches validiert werden soll.
     * @throws ValidatorException wenn die Eingabe invalide ist.
     */
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        try {
            mail = (String) o;
            password = (String) uiComponent.getAttributes().get("password");

            if (mail.isEmpty() && password.isEmpty()) {
                throw new ValidatorException(new FacesMessage("Bitte gib Deine Login-Daten ein!"));
            }
            else if(!validateNutzer(mail,HashedPasswordGenerator.generateHash(password))){
                throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
            }
            return;
        } catch (NullPointerException e) {

            throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
        }
    }

    /**
     * Validiert anhand der &uuml;bergebenen Parameter, ob ein passender Eintrag in der Datenbank existiert.
     *
     * @param mail Die E-Mail-Adresse des Nutzers.
     * @param password Das Passwort des Nutzers.
     * @return Gibt true zur&uuml;ck, wenn es einen Eintrag in der Datenbank gibt, andernfalls wird ein false.
     */
    private boolean validateNutzer(String mail, String password) {
        try {
            nutzer = dao.findNutzerByMail(mail);
            return password.equals(nutzer.getPasswort());
        } catch (NullPointerException e) {
            return false;
        }
    }
}
