package validators;

import dao.DAO;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("suchanfrageValidator")
public class SuchanfrageValidator implements Validator {

    @EJB
    DAO dao;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
//        if(!dao.find)
    }
}
