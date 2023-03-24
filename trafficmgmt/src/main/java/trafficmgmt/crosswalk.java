package trafficmgmt;
import trafficmgmt.Intersection.direction;

public class crosswalk {
    private direction direction; 
    private String crosswalkState; // stop or walk or countdown
    private int crosswalkCountDownNumber;

    public crosswalk(direction direction) { 
        this.direction = direction;
    }

    public void stopSignal() { 
        this.crosswalkState = "stop";
    }

    public void walkSignal() { 
        this.crosswalkState = "walk";
    }

    public void countDownInit(int countDownFrom) { 
        this.crosswalkState = "countdown";
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
