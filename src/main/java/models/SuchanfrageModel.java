package models;

public class SuchanfrageModel {

    // =========================== Class Variables ===========================79
    // =============================  Variables  =============================79
    private String searchedSpracheName;
    private int minAlter;
    private int maxAlter;
    private Geschlecht searchedGeschlecht;

    // ============================  Constructors  ===========================79

    public SuchanfrageModel(String searchedSpracheName, int minAlter, int maxAlter, Geschlecht searchedGeschlecht) {
        this.searchedSpracheName = searchedSpracheName;
        this.minAlter = minAlter;
        this.maxAlter = maxAlter;
        this.searchedGeschlecht = searchedGeschlecht;
    }

    // ===========================  public  Methods  =========================79

    public String getSearchedSpracheName() {
        return searchedSpracheName;
    }

    public void setSearchedSpracheName(String searchedSpracheName) {
        this.searchedSpracheName = searchedSpracheName;
    }

    public int getMinAlter() {
        return minAlter;
    }

    public void setMinAlter(int minAlter) {
        this.minAlter = minAlter;
    }

    public int getMaxAlter() {
        return maxAlter;
    }

    public void setMaxAlter(int maxAlter) {
        this.maxAlter = maxAlter;
    }

    public Geschlecht getSearchedGeschlecht() {
        return searchedGeschlecht;
    }

    public void setSearchedGeschlecht(Geschlecht searchedGeschlecht) {
        this.searchedGeschlecht = searchedGeschlecht;
    }

    // =================  protected/package local  Methods ===================79
    // ===========================  private  Methods  ========================79
    // ============================  Inner Classes  ==========================79
    // ============================  End of class  ===========================79
}
