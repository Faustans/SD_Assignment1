package com.sd.assignement1.entities;


import com.sd.assignement1.sharedRegions.*;
import java.util.*;


/**
 * This datatype implements the Passenger thread.
 * In his lifecycle, the passenger breaks his car,
 * gets (or not) a replacement car, and gets his
 * car fixed. <p>
 * He follows the following order:
 * <ul>
 * <li> Uses his car until it breaks down;
 * <li> Goes to repair shop and queues in;
 * <li> Asks (or not) the manager for a replacement car;
 * <li> Goes back to work with a replacement car or by bus;
 * <li> After the manager calls, the customer goes back to the shop;
 * <li> Finally the customer pays for the service, and gets the car back.
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

    private static Queue<Passenger> places = new PriorityQueue<>();

    private static int passengerInTheBus = 0;

    public boolean canGo = false;


    public BusDriver(Repository repo, ArrivalTerminalTQuay a,DepartureTerminalTQuay d){
        super("BusDriver ");
        this.arrivalTerminalTQuay = a;
        this.timerWait = new Timer();
        this.departureTerminalTQuay = d;
        this.state = States.PKAT;
    }

    @Override
    /**
     * BusDriver lifecycle
     */
    public void run(){
        while( repo.hasDaysWorkEnded != false){
            switch (state){

                case PKAT:
                    this.timerWait = new Timer();
                    this.timerWait.schedule(new TimerWaitingForPassenger(), 0, 500);
                    while(canGo!=true){
                        Passenger currentPassenger = arrivalTerminalTQuay.queueOut();
                        enterInTheBus(currentPassenger);
                    }
                    goToDepartureTerminal();
                    break;
                 case PKDT:
                    while(canGo!=true){
                        departureTerminalTQuay.queueIn(exitInTheBus());
                        goToArrivalTerminal();
                    }
                    break;
            }
        }

    }

    public void enterInTheBus(Passenger p){
        if(passengerInTheBus==2){
            canGo=true;
        }
        else{
            passengerInTheBus++;
            places.push(p);
        }

    }
    public Passenger exitInTheBus(){
        Passenger p = new Passenger();
        if(passengerInTheBus==0){
            canGo=true;
        }
        else{
            passengerInTheBus--;
            p = places.pop();
        }
        return p;


    }


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
     *
     * @return BusDriver currenter state
     */
    public States getBusDriverState(){
        return state;
    }

    public void setState(States s){
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        this.state = s;
    }

    public void goToDepartureTerminal(){
        setState(States.DF);
    }

    public void goToArrivalTerminal(){
        setState(States.DB);
    }

    public void parkTheBusDepartureTerminal(){
        setState(States.PKAT);
    }

    public void parkTheBusArrivalTerminal(){
        setState(States.PKDT);
    }




}
