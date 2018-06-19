package models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

/**
 * Bildet eine Suchanfrage-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findSuchanfrageByNutzer", query = "SELECT suchanfrage " +
                                                                      "FROM Suchanfrage suchanfrage " +
                                                                      "WHERE suchanfrage.nutzer = :nutzer"),
                @NamedQuery(name = "findSuchanfrage", query = "SELECT suchanfrage " +
                                                              "FROM Suchanfrage suchanfrage " +
                                                              "WHERE suchanfrage.paramSpracheID = :spracheId AND suchanfrage.nutzer.id = :nutzerId"),
                @NamedQuery(name = "deleteSuchanfrage", query = "DELETE FROM Suchanfrage suchanfrage" +
                                                                "WHERE suchanfrage.id = :id"),
                @NamedQuery(name = "deleteSuchanfrageByNutzer", query = "DELETE FROM Suchanfrage suchanfrage " +
                                                                        "WHERE suchanfrage.nutzer = :nutzer")})
public class Suchanfrage {

    /**
     * Repr&auml;sentiert die ID der Suchanfrage-Entit&auml;t.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int suchId;

    /**
     * Repr&auml;sentiert das paramSpracheID-Attribut der Suchanfrage-Entit&auml;t.
     */
    @Column(nullable = false)
    private int paramSpracheID;

    /**
     * Repr&auml;sentiert das paramAlterMin-Attribut der Suchanfrage-Entit&auml;t.
     */
    private byte paramAlterMin;

    /**
     * Repr&auml;sentiert das paramAlterMax-Attribut der Suchanfrage-Entit&auml;t.
     */
    private byte paramAlterMax = 100;

    /**
     * Repr&auml;sentiert das paramGeschlecht-Attribut der Suchanfrage-Entit&auml;t.
     */
    @Enumerated(EnumType.STRING)
    private Geschlecht paramGeschlecht;

    /**
     * Repr&auml;sentiert das nutzer_Id-Attribut der Suchanfrage-Entit&auml;t.
     */
    @ManyToOne
    private Nutzer nutzer;

    /**
     * Gibt die ID der Suchanfrage-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public int getSuchId() {
        return suchId;
    }

    /**
     * Gibt das paramSpracheID-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das paramSpracheID-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public int getParamSpracheID() {
        return paramSpracheID;
    }

    /**
     * Ersetzt das paramSpracheID-Attribut durch ein neues paramSpracheID-Attribut.
     *
     * @param paramSpracheID Der {@code int}-Wert mit dem neuen paramSpracheID-Attribut, welches das alte paramSpracheID-Attribut ersetzt.
     */
    public void setParamSpracheID(int paramSpracheID) {
        this.paramSpracheID = paramSpracheID;
    }

    /**
     * Gibt das paramAlterMin-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das paramAlterMin-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public byte getParamAlterMin() {
        return paramAlterMin;
    }

    /**
     * Ersetzt das paramAlterMin-Attribut durch ein neues paramAlterMin-Attribut.
     *
     * @param paramAlterMin Der {@code byte}-Wert mit dem neuen paramAlterMin-Attribut, welches das alte paramAlterMin-Attribut ersetzt.
     */
    public void setParamAlterMin(byte paramAlterMin) {
        this.paramAlterMin = paramAlterMin;
    }

    /**
     * Gibt das paramAlterMax-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das paramAlterMax-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public byte getParamAlterMax() {
        return paramAlterMax;
    }

    /**
     * Ersetzt das paramAlterMax-Attribut durch ein neues paramAlterMax-Attribut.
     *
     * @param paramAlterMax Der {@code byte}-Wert mit dem neuen paramAlterMax-Attribut, welches das alte paramAlterMax-Attribut ersetzt.
     */
    public void setParamAlterMax(byte paramAlterMax) {
        this.paramAlterMax = paramAlterMax;
    }

    /**
     * Gibt das paramGeschlecht-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt das paramGeschlecht-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public Geschlecht getParamGeschlecht() {
        return paramGeschlecht;
    }

    /**
     * Ersetzt das paramGeschlecht-Attribut durch ein neues paramGeschlecht-Attribut.
     *
     * @param paramGeschlecht Das {@code Geschlecht}-Objekt mit dem neuen paramGeschlecht-Attribut, welches das alte paramGeschlecht-Attribut ersetzt.
     */
    public void setParamGeschlecht(Geschlecht paramGeschlecht) {
        this.paramGeschlecht = paramGeschlecht;
    }

    /**
     * Repr&auml;sentiert das nutzer_Id-Attribut der Suchanfrage-Entit&auml;t.
     */
    /**
     * Gibt das nutzer_Id-Attribut der Suchanfrage-Entit&auml;t in Form eines {@code Nutzer}-Objektes zur&uuml;ck.
     *
     * @return Gibt das nutzer_Id-Attribut der Suchanfrage-Entit&auml;t zur&uuml;ck.
     */
    public Nutzer getNutzer() {
        return nutzer;
    }

    /**
     * Ersetzt das nutzer_Id-Attribut durch ein neues nutzer_Id-Attribut.
     *
     * @param nutzer Das {@code Nutzer}-Objekt mit dem neuen nutzer_Id-Attribut, welches das alte nutzer_Id-Attribut ersetzt.
     */
    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }
}
