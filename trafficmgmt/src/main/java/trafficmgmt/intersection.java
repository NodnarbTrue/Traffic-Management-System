package trafficmgmt;
import java.util.ArrayList;

abstract class Intersection {
    protected int intersectionID = generateID();
    protected String intersectionRoadOneName; 

    public enum direction {NORTH, EAST, SOUTH, WEST, DIRECTION_ONE, DIRECTION_TWO}
    protected direction currentGreenLightDirection; // The direction that the intersection is currently green for
    protected int currentGreenLightTimer; // The seconds left until the current green light turns red (this variable counts down)
    protected int currentCrosswalkLightTimer; // The seconds left until the current green light turns red (based on green light timer)

    protected int directionOneLightLength; // Length in seconds that the directionOne timer starts at once it turns green
    protected int minimumLightLength; // Value that when the current timer reaches, it cannot be shortened further
    protected ArrayList<trafficlight> directionOneTrafficLights; // 
    protected ArrayList<crosswalk> directionOneCrosswalks; //

    private int generateID() {
        //create a random Id generator and return an int
        return 1;
    }

    public Intersection(int directionOneLightLength, String intersectionRoadOneName) {
        this.directionOneLightLength = directionOneLightLength;
        this.intersectionRoadOneName = intersectionRoadOneName;
    }

    public abstract int startIntersection(); // Start the intersection (start counting down current green light)
    public abstract int stopIntersection(); // Stop the countdown of intersection lights, leave at current state

    // Optimization functions
    public abstract int inputOptimization();
    public abstract String viewOptimization();
    public abstract int applyOptimization();

    // Timing getters and setters
    public abstract void changeTrafficLightTiming(direction direction, int newLength); // Change light length of chosen direction
    public abstract void changeLeftTurnTiming(direction dirction, int newLength); // Change left turn length of chosen direction
    public abstract void changeCrossWalkTiming(direction direction, int newLength); // Change crosswalk length of chosen direction

    // Can't be implemented here since you need to check the other direction 
    // whose hashmap is specified in the appropriate subclasses
    public abstract int getTrafficLightTiming(direction direction);
    public abstract int getLeftTurnTiming(direction direction);
    public abstract int getCrossWalkTiming(direction direction);

    // Input related functions
    public abstract void shortenCurrentTrafficLightDuration(int timeToShortenBy); // Reduce current green light timer by a value
    public abstract void shortenCurrentCrossWalkDuration(int timeToShortenBy); // Reduce current crosswalk light timer by a value
    public abstract void pedestrianInput(direction requestedCrossingDirection); // Pedestrian interaction 
    public abstract void carWeightInput(direction startDirection, direction crossingDirection, int weight); // Car interaction
}