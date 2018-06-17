package validators;

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

// TODO Luis: 2018-06-17 Bitte beschreiben fuer die JavaDoc
@FacesValidator("spracheValidatorSuchanfrage")
public class SpracheValidatorSuchanfrage implements Validator {

    private Nutzer nutzer;
    private int sprachID = 0;
    public SpracheValidatorSuchanfrage() { initNutzer(); }


    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        sprachID = Integer.parseInt(o.toString());

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
        nutzer = loginManagedBean.getNutzer();
    }
}
