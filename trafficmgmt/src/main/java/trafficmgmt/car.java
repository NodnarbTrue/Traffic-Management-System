package trafficmgmt;

import java.security.Identity;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class car {

    // Variables
    protected int ID;
    protected String position;
    protected String directionGoing;

    // Constructor
    public car(int ID, String position, String directionGoing) {
        this.ID = ID;
        this.position = position;
        this.directionGoing = directionGoing;
    }

    // Methods
    public int addCarWeightSignal(String direction) {

    }

    public int removeCarWeightSignal(String direction) {

    }
}
