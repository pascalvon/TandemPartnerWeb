package models;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({ @NamedQuery(name = "findMatchanfragenByMail", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.partner = :vn AND matchanfragen.angenommen = 0"),
                @NamedQuery(name = "findMatchanfragenByAllColumns", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.angenommen = 1 AND (matchanfragen.partner = :vn OR matchanfragen.initiator = :vn)"),
                @NamedQuery(name = "deleteMatchanfrage", query = "DELETE FROM Matchanfragen matchanfrage WHERE matchanfrage.initiator = :in AND matchanfrage.partner = :pa"),
                @NamedQuery(name = "deleteMatchanfrageByNutzer", query = "DELETE FROM Matchanfragen  matchanfragen WHERE matchanfragen.initiator = :vn OR matchanfragen.partner = :vn")})
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
}
