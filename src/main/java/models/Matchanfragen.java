package models;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "findMatchanfragenByMailAndAngenommen", query = "SELECT matchanfragen FROM Matchanfragen matchanfragen WHERE matchanfragen.partner = :vn AND matchanfragen.angenommen = :ag")
// TODO Joe: 25.05.2018 Gibt es den Fall, dass Partner und fuer angenommen = 1 gesucht werden? Wenn nein, kann 'ab' raus.
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
