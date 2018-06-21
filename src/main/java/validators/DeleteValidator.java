package validators;

import jsfbeans.LoginManagedBean;
import models.Nutzer;
import utilities.HashedPasswordGenerator;

import javax.el.ELContext;
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
@FacesValidator("deleteValidator")
/**
 * Validator, welcher beim Löschen des Nutzers die Bestätigungseingabe (Password) validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class DeleteValidator implements Validator {
    /**
     * Nutzer-Instanz, welche zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer nutzer;
    /**
     * Konstruktor der Klasse, welcher den angemeldeten Nutzer initialisiert
     */
    public DeleteValidator(){
        initNutzer();
    }

    /**
     * Standardmethode, welche der Validator-Interface zu implementieren aufzwingt
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     *
     * Zuerst finden eine Typkonvertierung der Eingabe und eine anschließende Verschlüsselung statt, um den Wert
     * in die gleiche Form wie in der Datenbank zu bringen
     * Danach wird überprüft, ob die Eingabe dem Passwort des Nutzers entspricht
     * Falls die Eingabe dem hinterlegten Passwort nicht entspricht, schlägt der Validator an und die entsprechende
     * Fehlermeldung wird ausgegeben
     * Falls doch, ist die Validierung abgeschlossen
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String password = HashedPasswordGenerator.generateHash((String) o);

        if(!password.equals(nutzer.getPasswort())){
            throw new ValidatorException(new FacesMessage("Falsches Passwort!", "Profil konnte nicht gelöscht werden!"));
        }
        return;
    }

    //TODO: Joe JavDoc
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }
}
