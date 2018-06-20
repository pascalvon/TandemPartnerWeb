package models;

/**
 * Stellt das Geschlecht eines Nutzers dar.
 */
public enum Geschlecht {
    WEIBLICH("weiblich"), MAENNLICH("männlich"), EGAL("weiblich und männlich");

    /**
     * Anzeigename eines  {@code Geschlecht}-Enums.
     */
    private String anzeigename;

    /**
     * Initialisiert ein neu erzeugtes {@code Geschlecht}-Objekt mit einem Anzeigenamen.
     * @param anzeigename
     */
    private Geschlecht(String anzeigename) {
        this.anzeigename = anzeigename;
    }

    /**
     * Gibt den Anzeigenamen des {@code Geschlecht}-Enums wieder.
     * @return Gibt den Anzeigenamen des {@code Geschlecht}-Enums zur&uuml;ck.
     */
    public String getAnzeigename() {
        return anzeigename;
    }

    /**
     * &Uuml;berschreibt die {@link #toString() toString}-Methode, sodass {@link #anzeigename anzeigename}
     * zur&uuml;ckgegeben wird.
     *
     * @return Gibt den Namen des {@code Geschlecht}-Enums zur&uuml;ck.
     */
    @Override
    public String toString() {
        return anzeigename;
    }

    /**
     * Pr&uuml;ft, ob es sich um die selben {@code Geschlecht}-Enums handelt.
     *
     * @param   geschlecht Das {@code Geschlecht}-Enum, mit dem verglichen werden soll.
     * @return  Gibt true zur&uuml;ck, wenn es sich um das selbe Geschlecht handelt und false, falls es dies nicht tut.
     */
    public boolean isSameGeschlecht(Geschlecht geschlecht) {
        return geschlecht == EGAL || geschlecht.equals(this);
    }
}
