package trafficmgmt;

import java.security.Identity;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class pedestrian {

    // Variables
    protected int ID;
    protected String directionGoing;
    protected String position;

    // Constructor
    public pedestrian(int ID, String directionGoing, String position) {
        this.ID = ID;
        this.directionGoing = directionGoing;
        this.position = position;
    }

    // Methods
    public void clickCrosswalkButton(String directionGoing) {
        intersection.switchDirection();
    }
}