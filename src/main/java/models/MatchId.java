package models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Bildet eine Matchanfragen-ID als Objekt in Java ab.
 */
@Embeddable
public class MatchId implements Serializable {

    /**
     * Repr&auml;sentiert die ID des Initiators der Matchanfrage.
     */
    private int initiator;

    /**
     * Repr&auml;sentiert die ID des Partners der Matchanfrage.
     */
    private int partner;

    /**
     * Repr&auml;sentiert die Sprache-ID der Matchanfrage.
     */
    private int spracheID;

    /**
     * Gibt die ID des Initiators der Matchanfrage zur&uuml;ck.
     *
     * @return Gibt die ID des Initiators der Matchanfrage zur&uuml;ck.
     */
    public int getInitiator() {
        return initiator;
    }

    /**
     * Ersetzt die ID des Initiators der Matchanfrage mit einer neuen Initiator-ID.
     *
     * @param initiator Neue Inititator-ID, welche die alte ID des Initiators ersetzt.
     */
    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    /**
     * Gibt die ID des Partners der Matchanfrage zur&uuml;ck.
     *
     * @return Gibt die ID des Partners der Matchanfrage zur&uuml;ck.
     */
    public int getPartner() {
        return partner;
    }

    /**
     * Ersetzt die ID des Partners der Matchanfrage mit einer neuen Partner-ID.
     *
     * @param partner Neue Partner-ID, welche die alte ID des Partner ersetzt.
     */
    public void setPartner(int partner) {
        this.partner = partner;
    }

    /**
     * Gibt die Sprache-ID der Matchanfrage zur&uuml;ck.
     *
     * @return Gibt die Sprache-ID der Matchanfrage zur&uuml;ck.
     */
    public int getSpracheID() {
        return spracheID;
    }

    /**
     * Ersetzt die Sprache-ID der Matchanfrage mit einer neuen Sprache-ID.
     *
     * @param spracheID Neue Sprache-ID, welche die alte Sprache-ID ersetzt.
     */
    public void setSpracheID(int spracheID) {
        this.spracheID = spracheID;
    }
}
