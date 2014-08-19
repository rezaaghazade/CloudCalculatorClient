package domain;

import java.io.Serializable;

/**
 * Created by reza on 8/10/14.
 */
public class Users implements Serializable{

    public String ID;
    public Integer Type;
    public String Passwd;
    public String Name;
    public String Family;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }
}

