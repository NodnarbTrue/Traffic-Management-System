package trafficmgmt;

import java.security.Identity;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class pedestrian {

    // Variables
    protected int ID;
    protected direction directionGoing;
    protected String position;

    // Constructor
    public pedestrian(int ID, direction directionGoing, String position) {
        this.ID = ID;
        this.directionGoing = directionGoing;
        this.position = position;
    }

    // Methods
    public void clickCrosswalkButton(direction directionGoing) {
        intersection.pedestrianInput(directionGoing);
    }
}