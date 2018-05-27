package models;

import javax.persistence.Id;
import java.io.Serializable;

public class MatchanfragenPK implements Serializable {

    @Id
    private String initiator;

    @Id
    private String partner;

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
}
