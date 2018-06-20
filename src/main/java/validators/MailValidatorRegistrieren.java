package validators;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
 * Da der von JSF bereitgestellte @FacesValidator nicht von dem "injection container"
 * (hier noch mal nach richtigem Begriff suchen ) gemanaged wird, muss zum Einsatz des DAO der MailValidatorRegistrieren als
 * @ManagedBean mit entsprechendem Scope annotiert werden.
 */
@ManagedBean
@RequestScoped
/**
 * Validator, der auf der "Registrieren"-Seite die E-Mail-Adress-Eingabe des Nutzers
 * nach entsprechenden Vorgaben validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class MailValidatorRegistrieren implements Validator {

    /**
     * Das DAO-Objekt, welches die Methoden zum Datenbankzugriff bereitstellt
     * Zur Injektion mit @EJB annotiert
     */
    @EJB
    private DAO dao ;
    /**
     * mail: Deklaration der zur Laufzeit mit Eingaben belegten E-Mail-Adresse
     */
    private String mail;
    /**
     * E-Mail-Pattern, welches zum Abgeleich verwendet wird
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Standardmethode, welche das Validator-Interface zu implementieren aufzwingt
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        /**
         * Typkonvertierung der Eingabe aus der entsprechenden Komponente, um Vergleichswert im richtigen Datentyp
         * (String) zu erhalten
         */
        mail = (String)o;
        /**
         * Bool'scher Wert, welcher beim "matchen" der Eingabe mit dem Pattern true gesetzt wird, ansonsten false
         */
        boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();

        /**
         * Überprüfung, ob Eingabe der E-Mail-Adresse leer ist
         */
        if(mail.isEmpty()){
            /**
             * @return bewirkt, dass das Argument "required=true" aus der .xhtml-Datei zum Tragen kommt
             */
            return;
        }
        /**
         * Bei E-Mail-Adress-Eingabe des Nutzers werden folgende Fälle überprüft:
         * 1.: Passt die eingegebene E-Mail-Adresse zu den Anforderungen (Pattern)? (Beim Anschlagen des Validators
         * folgt die Ausgabe der entsprechenden Fehlermeldung)
         * 2.: Wird diese Mail bereits von einem anderen Nutzer verwendet? (Beim Anschlagen des Validators
         * folgt die Ausgabe der entsprechenden Fehlermeldung)
         */
        else if(!matchesPattern) {
            throw new ValidatorException((new FacesMessage("Ungültige E-Mail-Adresse", "Bitte erneut eingeben!")));
        }
        else if(!dao.findNutzerByMailBoolean(mail)) {
            throw new ValidatorException((new FacesMessage("Diese E-Mail-Adresse wird bereits verwendet!")));
        }
    }
}
