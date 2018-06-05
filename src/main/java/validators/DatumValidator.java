package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;

@FacesValidator("datumValidator")
public class DatumValidator implements Validator {

    private Date geburtsdatum;
    private Date history = new Date();




    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        geburtsdatum = (Date) o;
        /*Setzen des Vergleichsdatums auf vor 13 Jahren.
        Da .setDay nicht vorhanden, wird per Differenzbildung der Stundenunterschied zum nächsten Tag ermittelt und addiert
        Das hat zufolge, dass der heutige Tag vor 13 Jahren eine valide Eingabe wird*/
        int diff = 23-history.getHours();
        history.setYear(history.getYear()-13);
        history.setHours(history.getHours()+diff);
        if(geburtsdatum.after(history)) {
            throw new ValidatorException(new FacesMessage("Die Anmeldung ist erst ab 13 Jahren erlaubt!"));
        }
        return;
    }

}
