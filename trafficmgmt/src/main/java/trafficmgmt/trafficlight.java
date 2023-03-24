package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;

public class trafficlight {
    private direction trafficlightDirection; 
    private lightState currentLightState; 
    private int rateOfCars;

    private Integer greenLightLength; 
    private Integer yellowLightLength; 
    
    private boolean leftTurnLight; 
    private lightState currentLeftTurnLightState;
    private Integer leftTurnLightLength;

    // Class constructor with only direction
    public trafficlight(direction directionOne) {
        this.trafficlightDirection = directionOne; 
        this.greenLightLength = null; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }

    // Class constructor without yellow light and left turn
    public trafficlight(direction direction, Integer greenLightLength) {
        this.trafficlightDirection = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }
   
    // Class constructor without yellow light length
    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength) {
        this.trafficlightDirection = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = true; 
        this.leftTurnLightLength = leftTurnLightLength;
   }

   // Class constructor with left turn and yellow light length
    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength, Integer yellowLightLength) {
         this.trafficlightDirection = direction; 
         this.greenLightLength = greenLightLength; 
         this.yellowLightLength = yellowLightLength; 
         this.leftTurnLight = true; 
         this.leftTurnLightLength = leftTurnLightLength;
    }

    public void turnGreen() { 
        this.currentLightState = lightState.GREEN;
    }

    public void turnRed() {
        this.currentLightState = lightState.RED;
    }

    public boolean checkIfLeftTurn() { 
        return this.leftTurnLight;
    }

    public void turnLeftRed() { 
        this.currentLeftTurnLightState = lightState.RED;
    }

    public void turnLeftGreen() { 
        this.currentLeftTurnLightState = lightState.GREEN;
    }

    public void setLightLength(lightState lightColour, int newLength) { 
        switch (lightColour){
            case GREEN:
                this.greenLightLength = newLength;
                break;
            case YELLOW:
                this.yellowLightLength = newLength;
                break;
            case LEFT_TURN:
                this.leftTurnLightLength = newLength;
                break;
            default:
                    break; 
            //Can add default to throw error
        }
    }

    public int getLightTiming(lightState lightColour){
        switch (lightColour){
            case GREEN:
                return greenLightLength;
            case LEFT_TURN:
                return leftTurnLightLength;
            default:
                return -1; //Can change to throw error
        }
    }
}
