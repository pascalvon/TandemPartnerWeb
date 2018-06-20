package models;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Bildet eine Matchanfragen-Entit&auml;t als Objekt in Java ab.
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findMatchanfragenByNutzerID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.id.partner = :partnerID AND matchanfragen.angenommen = 0"),
                @NamedQuery(name = "findMatchanfragenByAllColumns", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.angenommen = 1 AND (matchanfragen.id.partner = :nutzerID OR matchanfragen.id.initiator = :nutzerID)"),
                @NamedQuery(name = "findMatchanfragenByMatchanfragen", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen = :matchanfragen"),
                @NamedQuery(name = "findMatchanfragenByInitiatorPartnerSpracheID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.id.initiator = :initiatorID AND matchanfragen.id.partner = :partnerID AND matchanfragen.id.spracheID = :spracheID"),
                @NamedQuery(name = "deleteMatchanfrage", query = "DELETE FROM Matchanfragen matchanfragen WHERE matchanfragen.id = :matchID"),
                @NamedQuery(name = "deleteMatchanfrageByNutzer", query = "DELETE FROM Matchanfragen  matchanfragen WHERE matchanfragen.id.initiator = :nutzerID OR matchanfragen.id.partner = :nutzerID")})

public class Matchanfragen {

    /**
     * Repr&auml;sentiert die ID der Matchanfragen-Entit&auml;t.
     */
    @EmbeddedId
    private MatchId id = new MatchId();

    /**
     * Repr&auml;sentiert den Status der Matchanfragen-Entit&auml;t.
     */
    private Byte angenommen;

    public Matchanfragen() {

    }

    public Matchanfragen(MatchId matchId) {
        this.id = matchId;
    }

    /**
     * Gibt die ID der Matchanfragen-Entit&auml;t zur&uuml;ck.
     *
     * @return Gibt die ID der Matchanfragen-Entit&auml;t zur&uuml;ck.
     */
    public MatchId getId() {
        return id;
    }

    /**
     * Ersetzt den {@code byte}-Wert durch einen neuen {@code byte}-Wert.
     *
     * @param angenommen Der {@code byte}-Wert, welcher den alten {@code byte}-Wert ersetzt.
     */
    public void setAngenommen(Byte angenommen) {
        this.angenommen = angenommen;
    }

    /**
     * &Uuml;berschreibt die {@link #equals(Object) equals}-Methode, sodass {@code Matchanfragen}-Ojekte &uuml;ber
     * ihre {@link #id id} verglichen werden.
     *
     * @param o Das Objekt, mit dem verglichen werden soll.
     * @return Gibt true zur&uuml;ck, wenn beide Objekte die selbe ID besitzen und false, wenn sie eine
     * unterschiedliche ID besitzen.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matchanfragen that = (Matchanfragen) o;
        return Objects.equals(id, that.id);
    }

    /**
     * &Uuml;berschreibt die {@link #hashCode() hashCode}-Methode, sodass der Hashcode von {@code Matchanfragen}-
     * Objekten ihrer ID entspricht.
     *
     * @return Gibt den Hashcode des Objekts als seine ID wieder.
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

