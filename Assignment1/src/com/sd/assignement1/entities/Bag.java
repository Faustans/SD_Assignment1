package com.sd.assignement1.entities;

public class Bag {

    private int id;

    private int owner;

    private boolean collected;

    private String situation;

    public Bag(int id, int owner){
        this.id = id;
        this.owner = owner;
        this.collected = false;
    }

    public String getSituation(){
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public int getOwner() {
        return owner;
    }

    public int getId(){
        return id;
    }

    public void setCollected(){
        collected = true;
    }

    public boolean getCollected(){
        return collected;
    }
}
