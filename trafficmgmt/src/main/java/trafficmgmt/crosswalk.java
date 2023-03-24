package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;

public class crosswalk {
    private direction crosswalkDirection; 
    private crosswalkState crosswalkState;
    private int crosswalkCountDownNumber;

    public crosswalk(direction directionOne) { 
        this.crosswalkDirection = directionOne;
    }

    public crosswalk(direction direction, int countDownFrom) { 
        this.crosswalkDirection = direction;
        this.crosswalkCountDownNumber = countDownFrom;
    }



    public void stopSignal() { 
        this.crosswalkState = crosswalkState.STOP;
    }

    public void walkSignal() { 
        this.crosswalkState = crosswalkState.WALK;
    }


    // Used when the constructor includeded countDownFrom
    public void countDownInit() { 
        this.crosswalkState = crosswalkState.COUNTDOWN;
    }

    // Used when the constructor didn't include countDownFrom
    public void countDownInit(int countDownFrom) { 
        this.crosswalkState = crosswalkState.COUNTDOWN;
        this.crosswalkCountDownNumber = countDownFrom;
    }

    public void countDown() { 
        this.crosswalkCountDownNumber--;
    }

    
    public void setCrossWalkTiming(int newLength){
        this.crosswalkCountDownNumber = newLength;
    }

    public int getCrossWalkTiming(){
        return crosswalkCountDownNumber;
    }
}
