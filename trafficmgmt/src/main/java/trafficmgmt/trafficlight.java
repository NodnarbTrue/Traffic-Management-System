package trafficmgmt;
import trafficmgmt.Intersection.direction;

public class trafficlight {
    private direction direction; 
    private String currentLightState; 
    private int rateOfCars;

    private Integer greenLightLength; 
    private Integer yellowLightLength; 
    
    private boolean leftTurnLight; 
    private String currentLeftTurnLightState;
    private Integer leftTurnLightLength;

    // Class constructor without yellow light and left turn
    public trafficlight(direction direction, Integer greenLightLength) {
        this.direction = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }
   
    // Class constructor without yellow light length
    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength) {
        this.direction = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = true; 
        this.leftTurnLightLength = leftTurnLightLength;
   }

   // Class constructor with left turn and yellow light length
    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength, Integer yellowLightLength) {
         this.direction = direction; 
         this.greenLightLength = greenLightLength; 
         this.yellowLightLength = yellowLightLength; 
         this.leftTurnLight = true; 
         this.leftTurnLightLength = leftTurnLightLength;
    }

    public void turnGreen() { 
        this.currentLightState = "green";
    }

    public void turnRed() {
        this.currentLightState = "red";
    }

    public boolean checkIfLeftTurn() { 
        return this.leftTurnLight;
    }

    public void turnLeftRed() { 
        this.currentLeftTurnLightState = "red";
    }

    public void turnLeftGreen() { 
        this.currentLeftTurnLightState = "green";
    }

    public void setLightLength(String light, int newLength) { 
        switch (light){
            case "green":
                this.greenLightLength = newLength;
                break;
            case "left turn":
                this.leftTurnLightLength = newLength;
                break;
            //Can add default to throw error
        }
    }

    public int getLightTiming(String light){
        switch (light){
            case "green":
                return greenLightLength;
            case "left turn":
                return leftTurnLightLength;
            default:
                return -1; //Can change to throw error
        }
    }
}
