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
 * Der Validator, welcher beim L&ouml;schen des Nutzers die Best&auml;tigungseingabe (Password) validiert
 */
@FacesValidator("deleteValidator")
public class DeleteValidator implements Validator {

    /**
     * {@code Nutzer}-Objekt, welches zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer nutzer;
    /**
     * Konstruktor der Klasse, welcher den angemeldeten Nutzer initialisiert
     */
    public DeleteValidator(){
        initNutzer();
    }

    /**
     * Pr&uuml;ft, ob die Eingaben valide sind.
     *
     * @param facesContext Das {@code FacesContext}-Objekt, welches alle Statusinformationen der Anfrage enth&auml;lt.
     * @param uiComponent Das {@code UIComponent}-Objekt, welches die Basisklasse f&uuml;r alle
     *                    Oberfl&auml;chenkomponenten in JSF darstellt.
     * @param o Das Objekt, welches validiert werden soll.
     * @throws ValidatorException wenn die Eingabe invalide ist.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String password = HashedPasswordGenerator.generateHash(o.toString());

        if(!password.equals(nutzer.getPasswort())){
            throw new ValidatorException(new FacesMessage("Falsches Passwort!", "Profil konnte nicht gelöscht werden!"));
        }
        return;
    }

    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der
     * {@code LoginManagedBean} solange existiert, wie die Session l&auml;uft. Anschließend wird das
     * {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }
}
