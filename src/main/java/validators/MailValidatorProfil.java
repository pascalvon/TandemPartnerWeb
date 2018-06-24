package validators;

import dao.DAO;
import jsfbeans.LoginManagedBean;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
 * Der Validator, der in der {@code profil.xhtml} die E-Mail-Adressen-Eingabe des Nutzers validiert.
 */
@ManagedBean
@RequestScoped
public class MailValidatorProfil implements Validator {

    /**
     * Das {@code DAO}-Objekt, welches Methoden enth&auml;lt, um abfragen mit der Datenbank zu realisieren.
     */
    @EJB
    private DAO dao;

    /**
     * {@code Nutzer}-Objekt, welches zur Laufzeit bei der Validierung initialisert wird
     */
    private Nutzer nutzer;

    /**
     * {@code String}, welcher die Eingabe der E-Mail-Adresse des Nutzers enth&auml;lt.
     */
    private String mail;

    /**
     * E-Mail-Pattern, welches zum Abgeleich der eingegebenen E-Mail-Adresse verwendet wird.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Konstruktor der Klasse, welcher den angemeldeten Nutzer initialisiert
     */
    public MailValidatorProfil(){ initNutzer();}

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
        mail = (String)o;
        boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();

        if(mail.isEmpty() || mail.equals(nutzer.getMail())){
            return;
        }
        else if(!matchesPattern) {
            throw new ValidatorException((new FacesMessage("Ungültige E-Mail-Adresse", "Bitte erneut eingeben!")));
        }
        else if(!dao.findNutzerByMailBoolean(mail)) {
            throw new ValidatorException((new FacesMessage("Diese E-Mail-Adresse wird bereits verwendet!")));
        }
    }

    /**
     * Holt sich das {@code Nutzer}-Objekt, welcher aufgrund der {@code @SessionScope}-Annotation der
     * {@code LoginManagedBean} solange existiert, wie die Session l&auml;uft. Anschließend wird das
     * {@code Nutzer}-Objekt der {@code LoginManagedBean} dem {@link #nutzer nutzer} zugewiesen.
     */
    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }
}
