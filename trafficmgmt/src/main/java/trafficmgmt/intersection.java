package trafficmgmt;

import java.util.ArrayList;
import java.util.UUID;
import trafficmgmt.utility.*;

abstract class Intersection {
    static final Boolean OVERWRITE_EXISTING_OPTIMIZATION = true;

    public String intersectionID;
    public intersectionType intersectionClassification;
    protected String intersectionRoadOneName;
    protected timer intersectionTimer;

    // The direction that the intersection traffic is currently flowing through
    protected direction currentDirection;

    // The current time in the timer. For debugging perposes
    public int curerntDirectionTiming;

    // Seconds left until the current green light turns red
    protected int currentGreenLightTimer;

    // Seconds left until the current green light turns red
    protected int currentCrosswalkLightTimer;

    // Length in seconds that the directionOne timer starts at
    protected int directionOneLightLength;

    public ArrayList<trafficlight> directionOneTrafficLights;
    public ArrayList<trafficlight> directionTwoTrafficLights;
    public ArrayList<crosswalk> directionOneCrosswalks;
    public ArrayList<crosswalk> directionTwoCrosswalks;

    // CONSTRUCTORS
    public Intersection(String intersectionRoadOneName) {
        this.intersectionID = generateID();
        this.intersectionRoadOneName = intersectionRoadOneName;
        this.directionOneLightLength = 100; // Defult value
        this.curerntDirectionTiming = 100;
        this.directionOneTrafficLights = new ArrayList<trafficlight>();
        this.directionTwoTrafficLights = new ArrayList<trafficlight>();
        this.directionOneCrosswalks = new ArrayList<crosswalk>();
        this.directionTwoCrosswalks = new ArrayList<crosswalk>();
    }

    public Intersection(int directionOneLightLength, String intersectionRoadOneName) {
        this.intersectionID = generateID();
        this.directionOneLightLength = directionOneLightLength;
        this.intersectionRoadOneName = intersectionRoadOneName;
        this.curerntDirectionTiming = directionOneLightLength;
        this.directionOneTrafficLights = new ArrayList<trafficlight>();
        this.directionTwoTrafficLights = new ArrayList<trafficlight>();
        this.directionOneCrosswalks = new ArrayList<crosswalk>();
        this.directionTwoCrosswalks = new ArrayList<crosswalk>();
    }

    // Method for generating a random ID
    private String generateID() {
        return UUID.randomUUID().toString();
    }

    // Start the intersection (start counting down current full direction length)
    public abstract int startIntersection();

    // Stop the countdown of intersection lights, leave at current state
    public abstract int stopIntersection();

    // Methods required for the intersection to be used by the timer class
    public abstract boolean getCurrentDirectionLeftTurnExistance();

    public abstract Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn);

    public abstract void setAllCurrentDirectionTrafficLights(lightState newState);

    public abstract void setAllCurrentDirectionCrosswalk(crosswalkState newState);

    public abstract void setCurrentDirectionCrossWalkTimer(int currentTimer);

    public abstract void switchDirection();

    // Get and set methods common to all types of intersections
    // Note that ones for traffic lights and left turns are needed for 3 and 4 way
    // intersections
    public abstract int getCrossWalkTiming(direction dir);

    public abstract void changeCrossWalkTiming(direction dir, int newLength);

    public abstract void setRoadOneName(String name);

    public abstract void setRoadTwoName(String name);

    // Input related functions
    public abstract void pedestrianInput(direction requestedCrossingDirection);

    public abstract void carWeightInput(direction startDirection, direction crossingDirection, int weight);

    public abstract void shortenDirectionDuration(int timeToShortenBy);

    // Optimization functions
    public abstract int inputOptimization(Integer[][] input);

    public abstract String viewOptimizationRecommendation();

    public abstract int applyOptimization();

    public abstract int getTimeToCountDownFrom();

    public void trainIncomingSignal() {
        return;
    }

    public void trainClearSignal() {
        return;
    }
}