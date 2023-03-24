package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;

abstract class Intersection {

    // VARIABLES
    static final Boolean OVERWRITE_EXISTING_OPTIMIZATION = true;
    protected int intersectionID = generateID();
    protected String intersectionRoadOneName;

    protected direction currentDirection; // The direction that the intersection traffic is currently flowing through
    protected int currentGreenLightTimer; // Seconds left until the current green light turns red (this variable counts
                                          // down)
    protected int currentCrosswalkLightTimer; // Seconds left until the current green light turns red (based on green
                                              // light timer)

    protected int directionOneLightLength; // Length in seconds that the directionOne timer starts at once it turns
                                           // green
    protected int minimumLightLength; // Value that when the current timer reaches, it cannot be shortened further

    protected ArrayList<trafficlight> directionOneTrafficLights; //
    protected ArrayList<crosswalk> directionOneCrosswalks; //

    // CONSTRUCTOR

    public Intersection(String intersectionRoadOneName) {
        this.intersectionRoadOneName = intersectionRoadOneName;
    }

    public Intersection(int directionOneLightLength, String intersectionRoadOneName) {
        this.directionOneLightLength = directionOneLightLength;
        this.intersectionRoadOneName = intersectionRoadOneName;
    }

    // METHODS

    private int generateID() {
        // create a random Id generator and return an int
        return 1;
    }

    public abstract int startIntersection(); // Start the intersection (start counting down current green light)
    public abstract int stopIntersection(); // Stop the countdown of intersection lights, leave at current state

    // Optimization functions
    public abstract int inputOptimization(Integer[][] input);

    public abstract String viewOptimizationRecommendation();

    public abstract int applyOptimization();

    public abstract int getTimeToCountDownFrom();



    // Timing getters and setters
    /* IMPLEMENT THESE IN 3 WAY AND 4 WAY
    public abstract void changeTrafficLightTiming(direction direction, int newLength);
    public abstract void changeLeftTurnTiming(direction dirction, int newLength); 
    public abstract void changeCrossWalkTiming(direction direction, int newLength);
    */



    // Can't be implemented here since you need to check the other direction
    // whose hashmap is specified in the appropriate subclasses
    public abstract int getTrafficLightTiming(direction direction);

    public abstract int getLeftTurnTiming(direction direction);

    public abstract int getCrossWalkTiming(direction direction);

    // Input related functions

    /* IMPLEMENT THESE IN 3 WAY AND 4 WAY
    public abstract void shortenCurrentTrafficLightDuration(int timeToShortenBy);
    public abstract void shortenCurrentCrossWalkDuration(int timeToShortenBy); 
    */

    public abstract void pedestrianInput(direction requestedCrossingDirection);
    public abstract void carWeightInput(direction startDirection, direction crossingDirection, int weight);
}