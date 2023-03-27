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
    protected direction directionGoing;
    protected Intersection assignedIntersection;

    // Constructor
    public car(int ID, String position, direction directionGoing, Intersection assignedIntersection) {
        this.assignedIntersection = assignedIntersection;
        this.ID = ID;
        this.position = position;
        this.directionGoing = directionGoing;
    }

    // Methods
    public void addCarWeightSignal(direction direction, direction crossingDirection, int weight,
            Intersection assignedIntersection) {
        assignedIntersection.carWeightInput(direction, crossingDirection, weight);
    }

    // might not be needed
    public void removeCarWeightSignal(direction direction) {

    }
}
