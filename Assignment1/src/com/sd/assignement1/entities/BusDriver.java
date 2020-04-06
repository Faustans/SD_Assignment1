package com.sd.assignement1.entities;


import com.sd.assignement1.sharedRegions.*;
import java.util.*;


/**
 * This datatype implements the BusDriver thread.
 * In his lifecycle, the BusDriver parking in the arrival terminal,
 * waiting for tha passenger, and go to departure terminal when the bus is full, or after a time
 *
 * He follows the following order:
 * <ul>
 * <li> parking at the arrival terminal
 * <li> driving forward
 * <li> parking at the departure terminal
 * <li> driving backward
 * </ul>
 */

public class BusDriver extends Thread {


    private static Timer timerWait;
    /**
     *
     * BusDriver's state
     */
    private States state;

    /**
     * Arrival Terminal Quay
     */
    private ArrivalTerminalTQuay arrivalTerminalTQuay;

    /**
     * Departure Terminal Quay
     */
    private DepartureTerminalTQuay departureTerminalTQuay;

    /**
     * Repository
     */
    private Repository repo;

    /**
     *  Deque Array that have the free places in the bus an the opossite
     */

    private static Deque<Passenger> places = new ArrayDeque<Passenger>();

    private static int passengerInTheBus = 0;

    public boolean canGo = false;

    /**
     * BusDriver instantiation
     * @param repo Repository
     * @param a ArrivalTerminalTQuay
     * @param d DepartureTerminalTQuay
     */

    public BusDriver(Repository repo, ArrivalTerminalTQuay a,DepartureTerminalTQuay d){
        super("BusDriver ");
        this.arrivalTerminalTQuay = a;
        this.timerWait = new Timer();
        this.departureTerminalTQuay = d;
        this.state = States.PKAT;
    }

    private synchronized void waitForArrivalQueue(){
        synchronized (arrivalTerminalTQuay){
            try {
                arrivalTerminalTQuay.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void notifyArrival(){
        synchronized (this){
            this.notifyAll();
        }
    }

    @Override
    /**
     * BusDriver lifecycle
     */
    public void run(){
        while(!hasDaysWorkEnded()){
            switch (state){

                case PKAT:
                    System.out.println("----------PKAT");
                    this.timerWait = new Timer();
                    this.timerWait.schedule(new TimerWaitingForPassenger(), 0, 500);
                    while(!canGo){
                        while(!arrivalTerminalTQuay.queueEmpty()){
                            enterInTheBus(arrivalTerminalTQuay.getPassenger());
                        }
                       // while(arrivalTerminalTQuay.queueEmpty() || !canGo){
                       //     waitForArrivalQueue();
                       // }
                    }
                    notifyArrival();
                    goToDepartureTerminal();
                    break;
                case DF:
                    System.out.println("----------driv F");
                    parkTheBusDepartureTerminal();
                    break;
                case DB:
                    System.out.println("----------driv b");
                    parkTheBusArrivalTerminal();
                    break;
                 case PKDT:
                     departureTerminalTQuay.setArrived(true);
                     System.out.println("----------PKDT");
                    while(canGo==true){
                        if(passengerInTheBus==0){
                            canGo=false;
                        }
                        else{
                            while (passengerInTheBus>0){
                                departureTerminalTQuay.queueIn(exitInTheBus());
                            }
                        }

                        goToArrivalTerminal();
                    }
                    break;
            }
        }

    }

    /**
     * Function to enter the bus, that change the flag "canGo" if the bus is full
     * @param p Passanger that won enter in the bus
     */
    public void enterInTheBus(Passenger p){
        if(passengerInTheBus==3){
            canGo=true;
        }
        else{
            passengerInTheBus++;
            places.addFirst(p);
        }

    }

    /**
     *
     * @return the passenger that exit of the bus
     */
    public Passenger exitInTheBus(){
        Passenger p;
        passengerInTheBus--;
        p = places.pop();
        return p;


    }

    /**
     * Timer that the busDriver wait
     */
    private void stopTimerWaitingForPassenger(){
        canGo = true;
        this.timerWait.cancel();
        this.timerWait.purge();
    }

    class TimerWaitingForPassenger extends TimerTask{
        int count = 0;
        public void run(){
            if(count < 4){
                count++;
            }
            else{
                canGo = true;
                stopTimerWaitingForPassenger();
            }
        }
    }

    /**
     *  Function that tell the busDriver when work is Ended
     * @return
     */
    private boolean hasDaysWorkEnded(){
        return false;
    }

    /**
     *
     * @return BusDriver currenter state
     */
    public States getBusDriverState(){
        return state;
    }

    /**
     *  Change the Bus Driver state
     * @param s atual state of BusDriver
     */
    public void setState(States s){
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        this.state = s;
    }

    /**
     * Change the state when go to Departure Terminal
     */
    public void goToDepartureTerminal(){
        setState(States.DF);
    }
    /**
     * Change the state when go to Arraival Terminal
     */
    public void goToArrivalTerminal(){
        departureTerminalTQuay.setArrived(false);
        setState(States.DB);
    }
    /**
     * Change the state when go to parkTheBusDepartureTerminal
     */
    public void parkTheBusDepartureTerminal(){
        setState(States.PKDT);
    }
    /**
     * Change the state when park bus in arrival terminal
     */
    public void parkTheBusArrivalTerminal(){
        setState(States.PKAT);
    }
    /**
     * Change the state enter in the bus
     */

    public static int getPassengerInTheBus(){
        return passengerInTheBus;
    }


}
