package trafficmgmt;

public class trafficlight {
    private String direction; 
    private String currentLightState; 
    private int rateOfCars;

    private int greenLightLength; 
    private int yellowLightLength = 3; 
    
    private boolean leftTurnLight; 
    private String currentLeftTurnLightState;
    private int leftTurnLightLength;

    // Class constructor without left turn
    public trafficlight(String direction, int greenLightLength, int yellowLightLength) {
        this.direction = direction; 
        this.greenLightLength = greenLightLength; 
        this.yellowLightLength = yellowLightLength; 
        this.leftTurnLight = false; 
        this.leftTurnLightLength = 0;
   }

   // Class constructor with left turn
    public trafficlight(String direction, int greenLightLength, int yellowLightLength, int leftTurnLightLength) {
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
}
