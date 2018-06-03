package models;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({ @NamedQuery(name = "findMatchanfragenByID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.partner = :partnerID AND matchanfragen.angenommen = 0"),
                @NamedQuery(name = "findMatchanfragenByAllColumns", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.angenommen = 1 AND (matchanfragen.partner = :id OR matchanfragen.initiator = :id)"),
                @NamedQuery(name = "findMatchanfragenByMatchanfragen", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen = :matchanfragen"),
                @NamedQuery(name = "findMatchanfragenByInitiatorPartnerSpracheID", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.initiator = :initiator AND matchanfragen.partner = :partner AND matchanfragen.spracheID = :spracheID"),
                @NamedQuery(name = "deleteMatchanfrage", query = "DELETE FROM Matchanfragen matchanfragen WHERE matchanfragen.initiator = :initiator AND matchanfragen.partner = :partner"),
                @NamedQuery(name = "deleteMatchanfrageByNutzer", query = "DELETE FROM Matchanfragen  matchanfragen WHERE matchanfragen.initiator = :id OR matchanfragen.partner = :id")})
@IdClass(MatchanfragenPK.class)
public class Matchanfragen {

    @Id
    private int initiator;

    @Id
    private int partner;

    @Id
    private int spracheID;

    private Byte angenommen;

    public int getInitiator() {
        return initiator;
    }

    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    public int getPartner() {
        return partner;
    }

    public void setPartner(int partner) {
        this.partner = partner;
    }

    public Byte getAngenommen() {
        return angenommen;
    }

    public void setAngenommen(Byte angenommen) {
        this.angenommen = angenommen;
    }

    public int getSpracheID() {
        return spracheID;
    }

    public void setSpracheID(int gesuchteSprache) {
        this.spracheID = gesuchteSprache;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matchanfragen that = (Matchanfragen) o;
        return spracheID == that.spracheID &&
                Objects.equals(initiator, that.initiator) &&
                Objects.equals(partner, that.partner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(initiator, partner, spracheID);
    }
}
