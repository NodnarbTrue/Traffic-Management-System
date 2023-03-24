package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;

public class crosswalk {
    private direction direction; 
    private crosswalkState crosswalkState;
    private int crosswalkCountDownNumber;

    public crosswalk(direction direction) { 
        this.direction = direction;
    }

    public crosswalk(direction direction, int countDownFrom) { 
        this.direction = direction;
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
