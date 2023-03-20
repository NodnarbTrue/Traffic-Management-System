package trafficmgmt;
import java.util.ArrayList;

abstract class Intersection {
    protected int intersectionID = generateID();
    protected String intersectionRoadOneName; 

    protected String currentGreenLightDirection;
    protected int currentGreenLightTimer;
    protected int currentCrosswalkLightTimer;

    protected int directionOneLightLength;
    protected ArrayList<String> directionOneTrafficLights;
    protected ArrayList<String> directionOneCrosswalks;

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
    public abstract void changeTrafficLightTiming(String direction, int newLength);
    public abstract void changeLeftTurnTiming(String dirction, int newLength);
    public abstract void changeCrossWalkTiming(String direction, int newLength);

    // Can't be implemented here since you need to check the other direction 
    // whose hashmap is spesified in the appropriate subclasses
    public abstract int getTrafficLightTiming(String direction);
    public abstract int getLeftTurnTiming(String direction);
    public abstract int getCrossWalkTiming(String direction);

    // Input related functions
    public abstract void shortenCurrentTrafficLightDuration(int timeToShortenBy);
    public abstract void shortenCurrentCrossWalkDuration(int timeToShortenBy);
    public abstract void pedestrianInput(String requestedCrossingDirection);
    public abstract void carWeightInput(String startDirection, String crossingDirection, int weight);
}