package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class AlterValidatorSuchanfrage implements Validator {

    private int alterMin;
    private int alterMax;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        alterMin = Integer.parseInt(o.toString());
        alterMax = Integer.parseInt(uiComponent.getAttributes().get("maxAlter").toString());

        try{
            if (alterMin>alterMax){
                throw new ValidatorException(
                        new FacesMessage("Das Maximalalter darf nicht über dem Mindestalter liegen!"));
            }
        } catch (Exception e){
            throw new ValidatorException(
                    new FacesMessage("Bitte sowohl ein Mindestalter als auch ein Maximalalter angeben!"));
        }return;

    }
}
