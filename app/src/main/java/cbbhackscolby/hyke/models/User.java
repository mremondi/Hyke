package cbbhackscolby.hyke.models;

import java.util.UUID;

/**
 * Created by mremondi on 2/25/17.
 */

public class User {
    public String fullName;
    public String currGroup;
    public Loc location;
    public boolean distress;

    public User(){}

    public User(String fullName){
        this.fullName = fullName;
        this.currGroup = null;
        this.location = new Loc();
        this.distress = false;
    }
}
