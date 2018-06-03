package models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MatchId implements Serializable {

    private int initiator;

    private int partner;

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
