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
 *
 * @ManagedBean mit entsprechendem Scope annotiert werden.
 */

@ManagedBean
@RequestScoped
/**
 * Validator, welcher die vom Anwender eingegebenen Login-Daten validiert.
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 *
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
     *
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent  enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o            beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     *
     * Zuerst werden die Parameter mail und password mit den Eingaben der jeweiligen Komponenten belegt
     * Anschließend werden beide Felder auf Eingaben überprüft, sind welche vorhanden, werden diese zur Validierung
     * in der Datenbank an die Methode validateNutzer übergeben
     * Schlägt die Validierung fehl, wird eine entsprchende Fehlermeldung ausgegeben
     * Ansonsten ist die Validierung beendet
     */
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        try {

            mail = (String) o;
            password = (String) uiComponent.getAttributes().get("password");

            if (mail.isEmpty() && password.isEmpty()) {

                throw new ValidatorException(new FacesMessage("Bitte gib Deine Login-Daten ein!"));
            } else if (!validateNutzer(mail, HashedPasswordGenerator.generateHash(password))) {

                throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
            }
            return;
        } catch (NullPointerException e) {

            throw new ValidatorException(new FacesMessage("E-Mail-Adresse oder Passwort falsch!"));
        }
    }

    /**
     * @param mail
     * @param password
     * @return
     *
     * Durchsucht die Datenbank anhand des übergebenen Parameters mail auf einen Eintrag und überprüft anschließend die
     * Passworteingabe mit dem zugehörigen Passwort des Eintrags
     * Abgefangen werden NullPointerExceptions, sollte kein Eintrag vorhanden sein
     */
    private boolean validateNutzer(String mail, String password) {

        try {

            nutzer = dao.findNutzerByMail(mail);

            return password.equals(nutzer.getPasswort());
        } catch (NullPointerException e) {

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
