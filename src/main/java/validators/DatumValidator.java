package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


/**
 * Annotiert mit dem JSF @FacesValidator, kann somit einer Komponente einer .xhtml-Seite
 * bspw. über den Tag <f:validator></f:validator> als Validator übergeben werden
 */
@FacesValidator("datumValidator")
/**
 * Validator, welcher das Alter des angemeldeten Nutzers nach entsprechenden Vorgaben validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class DatumValidator implements Validator {

    /**
     * Standardmethode, welche das Validator-Interface zu implementieren aufzwingt
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     */

    //TODO: Joe JavaDoc, die Funktonsweise der Methode beschreiben
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        LocalDate geburtstag = ((Date) o).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period p = Period.between(geburtstag, today);
        if (p.getYears() < 13) {
            throw new ValidatorException(new FacesMessage("Die Anmeldung ist erst ab 13 Jahren erlaubt!"));
        }
        return;
    }

}
