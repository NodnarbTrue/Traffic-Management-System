package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.crosswalkState;

public class crosswalk {
    private direction crosswalkDirection; 
    private crosswalkState crosswalkCurrentState;
    private int crosswalkCountDownNumber;
    private int currentcrosswalkCountDownNumber;

    // CONSTRUCTORS
    public crosswalk(direction directionOne) { 
        this.crosswalkDirection = directionOne;
        this.crosswalkCountDownNumber = 30;
        this.currentcrosswalkCountDownNumber = 30;
        this.crosswalkCurrentState = crosswalkState.STOP;
    }

    public crosswalk(direction direction, int countDownFrom) { 
        this.crosswalkDirection = direction;
        this.crosswalkCountDownNumber = countDownFrom;
        this.currentcrosswalkCountDownNumber = countDownFrom;
        this.crosswalkCurrentState = crosswalkState.STOP;
    }
    


    // Methods for changing the crosswalks to a state directly
    public void walkSignal() { 
        this.crosswalkCurrentState = crosswalkState.WALK;
        this.currentcrosswalkCountDownNumber = this.crosswalkCountDownNumber;
    }

    public void stopSignal() { 
        this.crosswalkCurrentState = crosswalkState.STOP;
        this.currentcrosswalkCountDownNumber = this.crosswalkCountDownNumber;
    }

    public void countDownSignal() { 
        this.crosswalkCurrentState = crosswalkState.COUNTDOWN;
    }

    public void setCurrentCrossWalkTiming(int countDownNumber) { 
        this.currentcrosswalkCountDownNumber = countDownNumber;
    }

    

    // Methods used by the intersections as per the timer class
    public void setCrossWalkState(crosswalkState newState) { 
        this.crosswalkCurrentState = newState;
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

    public crosswalkState getCurrentCrossWalkState() { 
        return this.crosswalkCurrentState;
    }

}
