package trafficmgmt;

import java.security.Identity;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class train {

    // Variables
    protected int ID;

    // Constructor
    public train(int ID) {
        this.ID = ID;
    }

    // Methods
    public void sendTrainSignal(twowayIntersecion twoway) {
        twoway.trainIncomingSignal();
    }

    public void clearTrainSignal(twowayIntersecion twoway) {
        twoway.trainClearSignal();
    }
}
