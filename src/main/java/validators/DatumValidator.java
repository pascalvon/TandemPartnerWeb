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
 * Der Validator f6uuml;r das Geburtsdatum beim registrieren eines Nutzers.
 */
@FacesValidator("datumValidator")
public class DatumValidator implements Validator {

    /**
     * Pr&uuml;ft, ob der Nutzer unter 13 Jahre alt ist. Falls ja, wird die {@code ValidatorException} geworfen
     * und der Nutzer wird an der Registrierung gehindert.
     *
     * @param   facesContext Das {@code FacesContext}-Objekt, welches alle Statusinformationen der Anfrage enth&auml;lt.
     * @param   uiComponent Das {@code UIComponent}-Objekt, welches die Basisklasse f&uuml;r alle Oberfl&auml;chenkomponenten
     *                      in JSF darstellt.
     * @param   o Das Objekt, welches validiert werden soll.
     * @throws  ValidatorException , wenn das Geburtsdatum ein Alter von < 13 darstellt.
     */
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
