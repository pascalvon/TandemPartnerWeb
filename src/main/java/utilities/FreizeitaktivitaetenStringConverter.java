package utilities;

import models.Freizeitaktivitaeten;
import models.Nutzer;

import java.util.ArrayList;

/**
 * Eine Hilfsklasse, um die selektierten Freizeitaktivit&auml;ten eines Nutzers in einen {@code String} zu packen.
 */
public class FreizeitaktivitaetenStringConverter {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79
    /**
     * Erzeugt zuerst eine {@code ArrayList} und initialisiert sie mit dem freizeitaktivitaetenSet des &uuml;bergebenen
     * {@code Nutzer}-Objektes. Anschlie&szlig;end werden die Bezeichnungen der Freizeitaktivit&auml;ten als
     * einzelnen {@code String} zur&uuml;ckgegeben.
     *
     * @param nutzer {@code Nutzer}-Objekt, dessen freizeitaktivitaetenSet zur Erzeugung des {@code String}'s verwendet
     *               wird.
     * @return  Gibt eine Zeichenkette mit allen Freizeitaktivit&auml;ten des &uuml;bergebenen {@code Nutzer}-Objektes
     *          zur&uuml;ck.
     */
    public static String selectedFreizeitaktivitaetenString(Nutzer nutzer) {
        ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>(nutzer.getFreizeitaktivitaetenSet());
        String[] selectedFreizeitaktivitaetenArray = new String[selectedFreizeitaktivitaetenList.size()];
        for (int i = 0; i < selectedFreizeitaktivitaetenList.size(); i++) {
            selectedFreizeitaktivitaetenArray[i] = String.valueOf(selectedFreizeitaktivitaetenList.get(i));
        }
        return String.join(",", selectedFreizeitaktivitaetenArray);
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
