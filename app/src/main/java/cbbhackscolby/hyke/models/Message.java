package cbbhackscolby.hyke.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mremondi on 2/25/17.
 */

public class Message {
    public String message;
    public String origin;
    public String dtime;

    public Message(){}
    
    public Message(String message, String sender){
        this.message = message;
        this.origin = sender;
        SimpleDateFormat simpleDate =  new SimpleDateFormat("h:mm a");
        this.dtime = simpleDate.format(new Date().getTime());
    }
}
