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


/**
 * Annotiert mit dem JSF @FacesValidator, kann somit einer Komponente einer .xhtml-Seite
 * bspw. über den Tag <f:validator></f:validator> als Validator übergeben werden
 */
@FacesValidator("spracheValidatorSuchanfrage")
/**
 * Validator, welcher die bei der Suchanfrage eingegebene Sprache validiert
 * Implementiert das @Validator-Interface, welches die Implementierung der Methode @validate aufzwingt
 */
public class SpracheValidatorSuchanfrage implements Validator {

    /**
     * Nutzer-Instanz, welche zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer nutzer;
    /**
     * Initialisierung der zu überprüfenden sprachID
     */
    private int sprachID = 0;
    /**
     * Konstruktor der Klasse, welcher den angemeldeten Nutzer initialisiert
     */
    public SpracheValidatorSuchanfrage() { initNutzer(); }


    /**
     * Standardmethode, welche der @FacesValidators zu implementieren aufzwingt
     * @param facesContext entspricht dem Kontext (Fenster/Seite)
     * @param uiComponent enspricht der Komponente, an welcher der Validator hängt (Button/InputText...)
     * @param o beinhaltet die Anwendereingaben der Komponente (Datentyp: Object)
     * @throws ValidatorException zeigt an, dass die Methode ValidatorExceptions werfen kann
     *
     * Nach Typkonvertierung der Eingabe (int) werden die gesprochenen Sprachen des Nutzer in ein Set des
     * Typs Sprache geladen
     * Sollte keine Eingabe gemacht worden sein, bleibt die sprachID auf dem Wert 0 und es wird eine entsprechende
     * Fehlermeldung ausgegeben
     * Falls eine Eingabe gemacht wurde, wird über das Set der gesprochenen Sprachen iteriert und jeder Eintrag
     * mit der Eingabe verglichen
     * Bei Gleichheit wird die entsprechende Fehlermeldung ausgegeben
     */
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

    //TODO: Joe JavaDoc
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }
}
