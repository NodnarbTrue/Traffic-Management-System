package trafficmgmt;
import java.util.ArrayList;

abstract class Intersection {
    protected int intersectionID = generateID();
    protected String intersectionRoadOneName; 

    public enum direction {NORTH, EAST, SOUTH, WEST, DIRECTION_ONE, DIRECTION_TWO}
    protected direction currentGreenLightDirection;
    protected int currentGreenLightTimer;
    protected int currentCrosswalkLightTimer;

    protected int directionOneLightLength;
    protected ArrayList<trafficlight> directionOneTrafficLights;
    protected ArrayList<crosswalk> directionOneCrosswalks;

    private int generateID() {
        //create a random Id generator and return an int
        return 1;
    }

    public Intersection(int directionOneLightLength, String intersectionRoadOneName) { 
        this.directionOneLightLength = directionOneLightLength;
        this.intersectionRoadOneName = intersectionRoadOneName;
    }

    public abstract int startIntersection();
    public abstract int stopIntersection();

    // Optimization functions
    public abstract int inputOptimization();
    public abstract String viewOptimization();
    public abstract int applyOptimization();

    // Timing getters and setters
    public abstract void changeTrafficLightTiming(direction direction, int newLength);
    public abstract void changeLeftTurnTiming(direction dirction, int newLength);
    public abstract void changeCrossWalkTiming(direction direction, int newLength);

    // Can't be implemented here since you need to check the other direction 
    // whose hashmap is spesified in the appropriate subclasses
    public abstract int getTrafficLightTiming(direction direction);
    public abstract int getLeftTurnTiming(direction direction);
    public abstract int getCrossWalkTiming(direction direction);

    // Input related functions
    public abstract void shortenCurrentTrafficLightDuration(int timeToShortenBy);
    public abstract void shortenCurrentCrossWalkDuration(int timeToShortenBy);
    public abstract void pedestrianInput(direction requestedCrossingDirection);
    public abstract void carWeightInput(direction startDirection, direction crossingDirection, int weight);
}