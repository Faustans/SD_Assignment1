package com.sd.assignement1.entities;

public class Bag {

    /**
     * Bag identifier,
     */
    private int id;

    /**
     * Bag owner
     */
    private int owner;


    /**
     * If the bag is collecter or not
     */
    private boolean collected;

    /**Bag instantiation
     * @param id
     * @param owner
     */

    public Bag(int id, int owner){
        this.id = id;
        this.owner = owner;
        this.collected = false;
    }

    /**
     * Return sitution of bag
     * @return bag situation
     */
    public String getSituation(){
        return situation;
    }

    /**
     *  set situation
     * @param disered situation
     */

    public void setSituation(String situation) {
        this.situation = situation;
    }

    /**
     * Return Owner of a bag
     * @return a owner
     */

    public int getOwner() {
        return owner;
    }

    /**
     * get bag id
     * @return id
     */

    public int getId(){
        return id;
    }

    /**
     * Set if bag is collected or not
     */

    public void setCollected(){
        collected = true;
    }

    /**
     *  return if the bag is collected or not
     * @return
     */

    public boolean getCollected(){
        return collected;
    }
}
