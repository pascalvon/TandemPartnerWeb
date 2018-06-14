package validators;

import dao.DAO;
import jsfbeans.LoginManagedBean;
import models.Nutzer;
import models.Sprache;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Set;

@FacesValidator("suchanfrageValidator")
public class spracheValidatorSuchanfrage implements Validator {

    private Nutzer nutzer;

    public spracheValidatorSuchanfrage() { initNutzer(); }

    int sprachID = 0;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        sprachID = Integer.parseInt((String) o);
        Set <Sprache> nutzerSpricht = nutzer.getSprachenSet();

        if(sprachID==0){
            throw new ValidatorException(new FacesMessage("Bitte eine Sprache eingeben!"));
        }

        for(Sprache s: nutzerSpricht){
            if (sprachID == s.getId()) {
                throw new ValidatorException(new FacesMessage("Diese Sprache sprichst Du doch bereits!"));
            }
        }
        return;
    }

    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
    }
}
