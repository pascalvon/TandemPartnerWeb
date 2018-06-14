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

@FacesValidator("datumValidator")
public class DatumValidator implements Validator {

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
