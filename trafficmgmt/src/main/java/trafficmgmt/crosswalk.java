package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;

public class crosswalk {
    private direction crosswalkDirection; 
    private crosswalkState crosswalkState;
    private int crosswalkCountDownNumber;
    private int currentcrosswalkCountDownNumber;

    // CONSTRUCTORS
    public crosswalk(direction directionOne) { 
        this.crosswalkDirection = directionOne;
        this.crosswalkCountDownNumber = 30;
        this.currentcrosswalkCountDownNumber = 30;
        this.crosswalkState = crosswalkState.STOP;
    }

    public crosswalk(direction direction, int countDownFrom) { 
        this.crosswalkDirection = direction;
        this.crosswalkCountDownNumber = countDownFrom;
        this.currentcrosswalkCountDownNumber = countDownFrom;
        this.crosswalkState = crosswalkState.STOP;
    }
    


    // Methods for changing the crosswalks to a state directly
    public void walkSignal() { 
        this.crosswalkState = crosswalkState.WALK;
        this.currentcrosswalkCountDownNumber = this.crosswalkCountDownNumber;
    }

    public void stopSignal() { 
        this.crosswalkState = crosswalkState.STOP;
        this.currentcrosswalkCountDownNumber = this.crosswalkCountDownNumber;
    }

    public void countDownSignal() { 
        this.crosswalkState = crosswalkState.COUNTDOWN;
    }

    

    // Methods used by the intersections as per the timer class
    public void setCrossWalkState(crosswalkState newState) { 
        this.crosswalkState = newState;
    }

    public void decrementTimer() { 
        this.currentcrosswalkCountDownNumber--;
    }


    
    // Methods for System adminstrator updating
    public void setCrossWalkTiming(int newLength){
        this.crosswalkCountDownNumber = newLength;
    }

    public int getCrossWalkTiming(){
        return this.crosswalkCountDownNumber;
    }

    public int getCurrentCrossWalkTiming() { 
        return this.currentcrosswalkCountDownNumber;
    }
}
