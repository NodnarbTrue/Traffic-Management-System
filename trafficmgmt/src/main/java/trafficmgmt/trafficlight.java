package trafficmgmt;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;

public class trafficlight {
    private direction trafficlightDirection; 
    private lightState currentLightState; 
    
    private Integer greenLightLength; 
    private Integer yellowLightLength; 
    private boolean leftTurnLight; 
    private Integer leftTurnLightLength;
    
    private int rateOfCars;

    // CONSTRUCTORS
    public trafficlight(direction directionOne) {
        this.trafficlightDirection = directionOne; 
        this.greenLightLength = null; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }

    public trafficlight(direction direction, Integer greenLightLength) {
        this.trafficlightDirection = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }
   
    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength) {
        this.trafficlightDirection = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = 3; 
        this.leftTurnLight = true; 
        this.leftTurnLightLength = leftTurnLightLength;
   }

    public trafficlight(direction direction, Integer greenLightLength, Integer leftTurnLightLength, Integer yellowLightLength) {
        this.trafficlightDirection = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = yellowLightLength; 
        this.leftTurnLight = true; 
        this.leftTurnLightLength = leftTurnLightLength;
    }


    
    // Methods for changing the trafficlight to a state directly
    public void turnGreen() { 
        this.currentLightState = lightState.GREEN;
    }

    public void turnRed() {
        this.currentLightState = lightState.RED;
    }

    public boolean checkIfLeftTurn() { 
        return this.leftTurnLight;
    }

    public void setLightState(lightState newLightState) { 
        this.currentLightState = newLightState;
    }



    // Methods for System adminstrator updating
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

    public lightState getCurrentLightState() { 
        return this.currentLightState;
    }

    public boolean getLeftTurnExsistance() { 
        return this.leftTurnLight;
    }
}
