package models;

import javax.persistence.Id;
import java.io.Serializable;

public class MatchanfragenPK implements Serializable {

    @Id
    private int initiator;

    @Id
    private int partner;

    @Id
    private int spracheID;

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

    public int getSpracheID() {
        return spracheID;
    }

    public void setSpracheID(int spracheID) {
        this.spracheID = spracheID;
    }
}
