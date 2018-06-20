package validators;

import dao.DAO;
import jsfbeans.LoginManagedBean;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
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
 * (hier noch mal nach richtigem Begriff suchen ) gemanaged wird, muss zum Einsatz des DAO der MailValidatorProfil als
 * @ManagedBean mit entsprechendem Scope annotiert werden.
 */
@ManagedBean
@RequestScoped
/**
 * Validator, der auf der "Profil bearbeiten"-Seite die E-Mail-Adress-Eingabe des Nutzers
 * nach entsprechenden Vorgaben validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class MailValidatorProfil implements Validator {

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
     * E-Mail-Pattern, welches zum Abgeleich verwendet wird
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Konstruktor der Klasse, welcher den angemeldeten Nutzer initialisiert
     */
    public MailValidatorProfil(){ initNutzer();}

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
        mail = (String)o;
        /**
         * Bool'scher Wert, welcher beim "matchen" der Eingabe mit dem Pattern true gesetzt wird, ansonsten false
         */
        boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();

        /**
         * Überprüfung, ob Eingabe der E-Mail-Adresse leer ist oder der aktuellen Mail entspricht
         */
        if(mail.isEmpty() || mail.equals(nutzer.getMail())){
            /**
             * Bei leerer Eingabe bewirkt @return, dass das Argument "required=true" aus der .xhtml-Datei zum Tragen kommt
             * Wenn die Eingabe des Nutzers seiner aktuellen E-Mail entspricht (= keine Änderungen),
             * braucht die Mail nicht weiter validiert zu werden (Annahme: Es stehen bereits nur valide E-Mail-Adressen in
             * der Datenbank)
             */
            return;
        }
        /**
         * Bei E-Mail-Adress-Änderung des Nutzers werden folgende Fälle überprüft:
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


    //TODO: Joe JavaDoc
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }

    /**
     * Getter und Setter
     */

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }
}
