package models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({ @NamedQuery(name = "findSuchanfrageByNutzer", query = "SELECT suchanfrage FROM Suchanfrage suchanfrage WHERE suchanfrage.nutzer = :nutzer"),
                @NamedQuery(name = "findSuchanfrage", query = "SELECT suchanfrage FROM Suchanfrage suchanfrage WHERE suchanfrage.paramSpracheID = :spracheId AND suchanfrage.nutzer.id = :nutzerId"),
                @NamedQuery(name = "deleteSuchanfrage", query = "DELETE FROM Suchanfrage suchanfrage WHERE suchanfrage.id = :id"),
                @NamedQuery(name = "deleteSuchanfrageByNutzer", query = "DELETE FROM Suchanfrage suchanfrage WHERE suchanfrage.nutzer = :nutzer")})
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

    public void addNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }

    public Nutzer getNutzer() {
        return nutzer;
    }
}
