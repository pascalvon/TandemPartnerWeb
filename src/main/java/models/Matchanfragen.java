package models;


import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({ @NamedQuery(name = "findMatchanfragenByMail", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.partner = :partnerMail AND matchanfragen.angenommen = 0"),
                @NamedQuery(name = "findMatchanfragenByAllColumns", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.angenommen = 1 AND (matchanfragen.partner = :mail OR matchanfragen.initiator = :mail)"),
                @NamedQuery(name = "findMatchanfragenByMatchanfragen", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen = :matchanfragen"),
                @NamedQuery(name = "findMatchanfragenByInitiatorAndPartner", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.initiator = :initiator AND matchanfragen.partner = :partner"),
                @NamedQuery(name = "deleteMatchanfrage", query = "DELETE FROM Matchanfragen matchanfragen WHERE matchanfragen.initiator = :initiator AND matchanfragen.partner = :partner"),
                @NamedQuery(name = "deleteMatchanfrageByNutzer", query = "DELETE FROM Matchanfragen  matchanfragen WHERE matchanfragen.initiator = :mail OR matchanfragen.partner = :mail")})
@IdClass(MatchanfragenPK.class)
public class Matchanfragen {

    @Id
    private String initiator;

    @Id
    private String partner;

    private Byte angenommen;

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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
        return Objects.equals(initiator, that.initiator) &&
                Objects.equals(partner, that.partner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(initiator, partner);
    }
}
