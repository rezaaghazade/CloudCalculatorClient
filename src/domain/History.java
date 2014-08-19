package domain;

import java.io.Serializable;

/**
 * Created by reza on 8/10/14.
 */
public class History implements Serializable {
    public String ID;
    public String opDate;
    public String opName;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }
}
