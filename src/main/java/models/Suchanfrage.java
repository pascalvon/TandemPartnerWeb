package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = "findSuchanfrageByNutzerID", query = "SELECT suchanfrage FROM Suchanfrage suchanfrage WHERE suchanfrage.nutzer = :vn")
public class Suchanfrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int suchId;

    @Column(nullable = false)
    private int paramSpracheID;

    private byte paramAlterMin;
    private byte paramAlterMax = 100;

    @Enumerated(EnumType.STRING)
    private Geschlecht paramGeschlecht;

    @ManyToOne
    private Nutzer nutzer;

    public int getSuchId() {
        return suchId;
    }

    public void setSuchId(int suchId) {
        this.suchId = suchId;
    }

    public int getParamSpracheID() {
        return paramSpracheID;
    }

    public void setParamSpracheID(int paramSpracheID) {
        this.paramSpracheID = paramSpracheID;
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



    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

}
