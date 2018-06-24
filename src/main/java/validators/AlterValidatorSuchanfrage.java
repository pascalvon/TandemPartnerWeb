package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("alterValidatorSuchanfrage")
public class AlterValidatorSuchanfrage implements Validator {

    private int alterMin;
    private int alterMax;

    /**
     * Standardmethode, welche der Validator-Interface zu implementieren aufzwingt
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     *
     * Die Eingabefelder der Altersparamter der Suchanfrage werden auf entsprechenden Inhalt geprüft.
     * Beim Anschlagen des Validators wird die entsprechende Fehlermeldung ausgegeben
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        if (o.toString().isEmpty() || uiComponent.getAttributes().get("maxAlter").toString().isEmpty())
        {
            throw new ValidatorException(new FacesMessage("Bitte sowohl ein Mindestalter als auch ein Maximalalter angeben!"));
        }else {

            alterMin = Integer.parseInt(o.toString());
            alterMax = Integer.parseInt(uiComponent.getAttributes().get("maxAlter").toString());

            if ((13 <= alterMin && 100 >= alterMin) && (13 <= alterMax && 100 >= alterMax)) {
                if (alterMin > alterMax) {
                    throw new ValidatorException(
                            new FacesMessage("Das Maximalalter darf nicht über dem Mindestalter liegen!"));
                }
            } else {
                throw new ValidatorException(new FacesMessage("Bitte ein Alter zwischen 13 und 100 angeben!"));
            }

        }
        return;

    }
}
