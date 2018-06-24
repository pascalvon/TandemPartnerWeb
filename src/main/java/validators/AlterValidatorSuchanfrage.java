package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Der Validator f&uuml;r die Alterseingabe beim Suchen nach anderen Nutzern.
 */
@FacesValidator("alterValidatorSuchanfrage")
public class AlterValidatorSuchanfrage implements Validator {

    /**
     * {@code int}-Wert, welcher das Mindestalter repr&auml;sentiert.
     */
    private int alterMin;

    /**
     * {@code int}-Wert, welcher das Maximalalter repr&auml;sentiert.
     */
    private int alterMax;

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
        if (o.toString().isEmpty() || uiComponent.getAttributes().get("maxAlter").toString().isEmpty()) {
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
