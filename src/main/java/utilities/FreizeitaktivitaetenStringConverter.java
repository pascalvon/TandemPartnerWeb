package utilities;

import models.Freizeitaktivitaeten;
import models.Nutzer;

import java.util.ArrayList;

public class FreizeitaktivitaetenStringConverter {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    // ============================  Constructors  ===========================79
    // ===========================  public  Methods  =========================79

    public static String selectedFreizeitaktivitaetenString(Nutzer nutzer) {
        ArrayList<Freizeitaktivitaeten> selectedFreizeitaktivitaetenList = new ArrayList<>(nutzer.getFreizeitaktivitaetenSet());
        String[] selectedFreizeitaktivitaetenArray = new String[selectedFreizeitaktivitaetenList.size()];
        for (int i = 0; i < selectedFreizeitaktivitaetenList.size(); i++) {
            selectedFreizeitaktivitaetenArray[i] = String.valueOf(selectedFreizeitaktivitaetenList.get(i).getId());
        }
        return String.join(",", selectedFreizeitaktivitaetenArray);
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
