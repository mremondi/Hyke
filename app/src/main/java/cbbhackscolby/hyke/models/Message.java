package cbbhackscolby.hyke.models;

import java.util.Date;

/**
 * Created by mremondi on 2/25/17.
 */

public class Message {
    public String message;
    public String origin;
    public long dtime;

    public Message(){}
    
    public Message(String message, String sender){
        this.message = message;
        this.origin = sender;
        this.dtime = new Date().getTime();;
    }
}
