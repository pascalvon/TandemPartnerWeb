package utilities;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Eine Hilfsklasse, um das Alter eines Nutzers zu berechnen.
 */
public class AgeCalculator {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    /**
     * Berechnet das Alter eines Nutzers anhand seines Geburtsdatums.
     * @param birthDate Geburtsdatum des Nutzers.
     * @return Gibt ein {@code int}-Wert, welches dem Alter des Nutzers entspricht zur&uuml;ck.
     */
    public static int calculateAge(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date currentDate = new Date();
        LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if ((birthLocalDate != null) && (currentLocalDate != null)) {
            return Period.between(birthLocalDate, currentLocalDate).getYears();
        } else {
            return 0;
        }
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
