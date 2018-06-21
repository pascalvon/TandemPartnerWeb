package models;

public enum Geschlecht {
    WEIBLICH("weiblich"), MAENNLICH("männlich"), EGAL("weiblich und männlich");

    private String anzeigename;

    private Geschlecht(String anzeigename) {
        this.anzeigename = anzeigename;
    }

    public String getAnzeigename() {
        return anzeigename;
    }


    @Override
    public String toString() {
        return anzeigename;
    }

    public boolean isSameGeschlecht(Geschlecht geschlecht) {
        return geschlecht == EGAL || geschlecht.equals(this);
    }
}
