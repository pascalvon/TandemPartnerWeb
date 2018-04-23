package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Suchanfrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int suchId;

    @Column(nullable = false)
    private String paramLernen;
    private byte paramAlterMin;
    private byte paramAlterMax;

    @Enumerated(EnumType.STRING)
    private Geschlecht paramGeschlecht;

    @Column(nullable = false)
    private String paramAktivitaet;

    @ManyToOne
    private Nutzer nutzer;

    public int getSuchId() {
        return suchId;
    }

    public void setSuchId(int suchId) {
        this.suchId = suchId;
    }

    public String getParamLernen() {
        return paramLernen;
    }

    public void setParamLernen(String paramLernen) {
        this.paramLernen = paramLernen;
    }

    public byte getParamAlterMin() {
        return paramAlterMin;
    }

    public void setParamAlterMin(byte paramAlterMin) {
        this.paramAlterMin = paramAlterMin;
    }

    public byte getParamAlterMax() {
        return paramAlterMax;
    }

    public void setParamAlterMax(byte paramAlterMax) {
        this.paramAlterMax = paramAlterMax;
    }

    public Geschlecht getParamGeschlecht() {
        return paramGeschlecht;
    }

    public void setParamGeschlecht(Geschlecht paramGeschlecht) {
        this.paramGeschlecht = paramGeschlecht;
    }

    public String getParamAktivitaet() {
        return paramAktivitaet;
    }

    public void setParamAktivitaet(String paramAktivitaet) {
        this.paramAktivitaet = paramAktivitaet;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

}
