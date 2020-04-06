package com.sd.assignement1.entities;

import java.util.ArrayDeque;
import java.util.Deque;

public class Plane {

    /**
     * Queue of the bags in the plane
     */
    private static Deque<Bag> bagStack = new ArrayDeque<Bag>();

    /**
     * Plane identifier
     */
    private static int id;

    /**
     * Number of the passenger in the plane
     */
    private static int numPassengers;

    /**
     * Number of the bags in the plane
     */
    private static int numBags;

    /**
     * Number of current Passenger in the plane
     */
    private static int currentPassengers;

    /**
     * Number of the current bags in the plane
     */
    private static int currentBags;

    /**
     * Boolean that confirm if the plane is landed or not
     */
    private static boolean landed;

    /**
     * Array with the passengers in the plane
     */
    private static Passenger[] passengers;

    /**
     *  Passenger instantiation
     * @param id Identifier of the plane
     * @param numPassengers Number of the passenger in the plane
     * @param landed boolean if plane is landed or not
     */
    public Plane(int id, int numPassengers, boolean landed){
        this.id = id;
        this.numPassengers = numPassengers;
        this.currentBags = 0;
        this.currentPassengers = 0;
        this.landed = landed;

    }


    /**
     *  Add a bag to the plane
     * @param b
     */
    public synchronized void addBag(Bag b){
        bagStack.add(b);
        numBags+=1;
        currentBags+=1;
    }

    /**
     * Get a bag from the plane
     * @return
     */
    public synchronized static Bag getBag(){
        currentBags--;
        return bagStack.getFirst();
    }

    /**
     *  Add a passeng to the plane
     * @param p
     */
    public synchronized void addPassenger(Passenger p){
        numPassengers+=1;
    }

    /**
     * confirm if the plane have passenger or if empty
     * @return
     */
    public synchronized boolean empty(){
        if(currentPassengers == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *  Confirm if the plane have baggs or if is empty
     * @return
     */
    public synchronized static boolean hasBags(){
        if(currentBags == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Passenger leave the plane
     */
    public synchronized void leave(){
        if(numPassengers>0 && landed()){
            numPassengers--;
        }
        else{
            System.err.println("Number of passengers leaving the plane incorrect");
        }
    }

    /**
     * Set if the plane is landed or not
     * @param landed
     */
    public synchronized void setLanded(boolean landed){
        this.landed = landed;
    }

    public synchronized boolean landed(){
        return this.landed;
    }

    /**
     * Get the current Bags in the plane
     * @return
     */
    public static int getCurrentBags(){
        return this.currentBags;
    }

}
