package models;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({ @NamedQuery(name = "findMatchanfragenByNutzerID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.id.partner = :partnerID AND matchanfragen.angenommen = 0"),
                @NamedQuery(name = "findMatchanfragenByAllColumns", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.angenommen = 1 AND (matchanfragen.id.partner = :nutzerID OR matchanfragen.id.initiator = :nutzerID)"),
                @NamedQuery(name = "findMatchanfragenByMatchanfragen", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen = :matchanfragen"),
                @NamedQuery(name = "findMatchanfragenByInitiatorPartnerSpracheID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.id.initiator = :initiatorID AND matchanfragen.id.partner = :partnerID AND matchanfragen.id.spracheID = :spracheID"),
                @NamedQuery(name = "deleteMatchanfrage", query = "DELETE FROM Matchanfragen matchanfragen WHERE matchanfragen.id = :matchID"),
                @NamedQuery(name = "deleteMatchanfrageByNutzer", query = "DELETE FROM Matchanfragen  matchanfragen WHERE matchanfragen.id.initiator = :nutzerID OR matchanfragen.id.partner = :nutzerID")})

public class Matchanfragen {

    @EmbeddedId
    private MatchId id;


    private Byte angenommen;

    public MatchId getId() {
        return id;
    }

    public void setId(MatchId id) {
        this.id = id;
    }

    public Byte getAngenommen() {
        return angenommen;
    }

    public void setAngenommen(Byte angenommen) {
        this.angenommen = angenommen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matchanfragen that = (Matchanfragen) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

