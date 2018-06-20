package validators;

import jsfbeans.LoginManagedBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Annotiert mit dem JSF @FacesValidator, kann somit einer Komponente einer .xhtml-Seite
 * bspw. über den Tag <f:validator></f:validator> als Validator übergeben werden
 */
@FacesValidator("passwordValidatorRegistrieren")
/**
 * Validator, welcher die Passworteingabe des Nutzers ("Registrieren") validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class PasswordValidatorRegistrieren implements Validator {

    /**
     * Standardmethode, welche der Validator-Interface zu implementieren aufzwingt
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
        String password = (String) o;
        /**
         * Typkonvertierung der Eingabe aus der Komponente "confirm", um Vergleichswert im richtigen Datentyp
         * (String) zu erhalten
         */
        String confirm = (String) uiComponent.getAttributes().get("confirm");

        /**
         * Überprüfung, ob mindestens eins der Felder leer ist
         */
        if (password.isEmpty() || confirm.isEmpty())
        {
            /**
             * @return bewirkt, dass das Argument "required=true" aus der .xhtml-Datei zum Tragen kommt
             */
            return;
        }
        /**
         * Bei der Passworteingabe wird nach 2 Kriterien validiert:
         * 1.: Entspricht das Passwort der vorgegebenen Länge? (Beim Anschlagen des Validators
         * folgt die Ausgabe der entsprechenden Fehlermeldung)
         * 2.: Stimmen beide Eingaben überein? (Beim Anschlagen des Validators
         * folgt die Ausgabe der entsprechenden Fehlermeldung)
         */
        if (password.length()< 8 || password.length()>30)        {
            throw new ValidatorException(new FacesMessage("Das Passwort muss zwischen 8 und 30 Zeichen lang sein!"));
        }
        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage("Passwörter stimmen nicht überein!"));
        }

    }

}