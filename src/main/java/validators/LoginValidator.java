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
 * Da der von JSF bereitgestellte @FacesValidator nicht von dem "injection container"
 * (hier noch mal nach richtigem Begriff suchen ) gemanaged wird, muss zum Einsatz des DAO der LoginValidator als
 * @ManagedBean mit entsprechendem Scope annotiert werden.
 */

@ManagedBean
@RequestScoped
/**
 * Validator, welcher die vom Anwender eingegebenen Login-Daten validiert.
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class LoginValidator implements Validator {

    /**
     * Das DAO-Objekt, welches die Methoden zum Datenbankzugriff bereitstellt
     * Zur Injektion mit @EJB annotiert
     */
    @EJB
    private DAO dao;
    /**
     * Nutzer-Instanz, welche zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer nutzer;
    /**
     * mail: Deklaration der zur Laufzeit mit Eingaben belegten E-Mail-Adresse
     */
    private String mail;
    /**
     * passwort: Deklaration des zur Laufzeit mit Eingaben belegten Passworts
     */
    private String password;

    /**
     * Standardmethode, welche das Validator-Interface zu implementieren aufzwingt. Auch ohne Annotation mit dem @FacesValidator
     * wsinnvoll einzusetzen.
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     */
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException
    {
        /**
         * try-catch-Block: try (versuche): Validierung der Eingaben
         */
        try{
            /**
             * @param mail wird mit der entsprechenden Anwendereingabe der Komponente belegt
             */
            mail = (String) o;
            /**
             * @param password wird mit dem Wert aus der Komponenten "password" belegt
             */
            password = (String) uiComponent.getAttributes().get("password");

            /**
             * Prüfung der Eingaben auf Inhalt oder Richtigkeit
             */
            if (mail.isEmpty() && password.isEmpty()){
                /**
                 * Sind beide Felder leer, wird nach dem Anschlagen des Validators die entsprechende Nachricht (@FacesMessage) ausgegeben
                 */
                throw new ValidatorException(new FacesMessage("Bitte gib Deine Login-Daten ein!"));
            }
            else if(!validateNutzer(mail,HashedPasswordGenerator.generateHash(password))){
                /**
                 * Schlägt der Validator aufgrund des Fehlschlagen der Validierung (@validateNutzer) an, wird die entsprechende Nachricht (@FacesMessage) ausgegeben
                 */
                throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
            }
            return;
        }
        catch (NullPointerException e)
        {
            /**
             * catch: Fängt eine durch die Validierung eventuell auftretende NullPointerException ab und gibt die entsprechende Nachricht (@FacesMessage) aus
             */
            throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
        }
    }

    /**
     *
     * @param mail
     * @param password
     * @return
     */
    private boolean validateNutzer(String mail, String password) {
        /**
         * try-catch: try(versuche!): Validiere die Eingaben durch Überprüfung der Datenbank
         */
        try {
            /**
             * Die Datenbank wird per Mail nach einem passenden Nutzer durchsucht, dieser wird in mit allen Information in @param nutzer geladen
             */
            nutzer = dao.findNutzerByMail(mail);
            /**
             * Bool'scher Vergleich, ob das in der Datenbank hinterlegte Passwort dem eingegebenen entspricht
             * Rückgabe des entsprechenden Zustands
             */
            return password.equals(nutzer.getPasswort());
        } catch (NullPointerException e) {
            /**
             * catch: Abfangen einer eventuell auftretenden NullPointerException bei der Datenbankabfrage
             */
            return false;
        }
    }

    /**
     * Getter und Setter
     */

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
