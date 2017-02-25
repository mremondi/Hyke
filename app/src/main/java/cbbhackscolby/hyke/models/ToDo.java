package cbbhackscolby.hyke.models;

/**
 * Created by mremondi on 2/25/17.
 */

public class ToDo {
    private String name;
    private String description;
    private boolean done;

    public ToDo(String name, String description){
        this.name = name;
        this.description = description;
        this.done = false;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setDone(boolean done){
        this.done = done;
    }
    public boolean getDone(){
        return done;
    }
}
