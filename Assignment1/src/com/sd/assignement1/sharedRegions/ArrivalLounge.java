package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;

/**
 * This datatype  implements the Arrival Loung shared memory region
 * The passenger when landed from the plane use this region to wait
 */

public class ArrivalLounge {

    private static Repository repo;

    public ArrivalLounge(Repository repo){
        this.repo = repo;
    }

    public void enter(Passenger p){

    }

}
