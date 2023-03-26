package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class fourwayIntersection extends Intersection {

    // VARIABLES

    // Name of second road
    protected String intersectionRoadTwoName;

    // Seconds left until the current green left light turns red (this variable counts down)
    protected int currentLeftLightTimer; 

    // Length in seconds that the directionTwo timer starts at once it turns green
    protected int directionTwoLightLength; 
    protected int directionOneLeftLightLength; 
    protected int directionTwoLeftLightLength; 

    // List of trafficlight objects
    protected ArrayList<trafficlight> directionTwoTrafficLights;

    
    /**
     * Constructor method of the four way intersection class
     * @param directionOneLightLength: Length of directionOne's green light
     * @param directionTwoLightLength: Length of directionTwo's green light
     * @param directionOneLeftLightLength: Length of directionOne's left green light
     * @param directionTwoLeftLightLength: Length of directionTwo's left green light
     * @param intersectionRoadOneName: Name of directionOne's road
     * @param intersectionRoadTwoName: Name of directionTwo's road
     */
    public fourwayIntersection(
    int directionOneLightLength, int directionTwoLightLength, 
    int directionOneLeftLightLength, int directionTwoLeftLightLength,
    String intersectionRoadOneName, String intersectionRoadTwoName) {

        super(directionOneLightLength, intersectionRoadOneName);
        this.directionTwoLightLength = directionTwoLightLength;
        this.intersectionRoadTwoName = intersectionRoadTwoName;
        this.directionOneLeftLightLength = directionOneLeftLightLength;
        this.directionTwoLeftLightLength = directionTwoLeftLightLength;

        // Loop twice since there are 2 trafficlight and crosswalk objects for each direction
        for (int i = 1; i <= 2; i++) {

            // directionOne has 2 trafficlight objects, if left light length is 0, no left turn signal
            if (directionOneLeftLightLength == 0) {
                directionOneTrafficLights.add(new trafficlight(direction.DIRECTION_ONE, directionOneLightLength)); }
            else {
                directionOneTrafficLights.add(new trafficlight(direction.DIRECTION_ONE, directionOneLightLength, directionOneLeftLightLength)); }

            // directionTwo has 2 trafficlight objects, if left light length is 0, no left turn signal
            if (directionTwoLeftLightLength == 0) {
                directionTwoTrafficLights.add(new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength)); }
            else {
                directionTwoTrafficLights.add(new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength, directionTwoLeftLightLength)); }

            // directionOne has 2 crosswalk objects
            directionOneCrosswalks.add(new crosswalk(direction.DIRECTION_ONE));

            // directionTwo has 2 crosswalk objects
            directionTwoCrosswalks.add(new crosswalk(direction.DIRECTION_TWO));
        }   
    }

    // METHODS

    public int startIntersection() {
        this.currentDirection = direction.DIRECTION_ONE;

        for (trafficlight i : directionOneTrafficLights) {
            i.turnGreen();
        }
        for (crosswalk i : directionOneCrosswalks) {
            i.walkSignal();
        }
        for (crosswalk i : directionTwoCrosswalks) {
            i.stopSignal();
        }
        return 1;
    };

    public int stopIntersection() {

        for (trafficlight i : directionOneTrafficLights) {
            i.turnRed();
        }
        for (crosswalk i : directionOneCrosswalks) {
            i.stopSignal();
        }
        for (crosswalk i : directionTwoCrosswalks) {
            i.stopSignal();
        }

        return 1;
    }

    public void changeTrafficLightTiming(direction direction, int newLength) {

    }

    public void changeLeftTurnTiming(direction direction, int newLength) {

    }

    public void changeCrossWalkTiming(direction direction, int newLength) {

    }

    public int getTrafficLightTiming(direction direction) {

        return 1;
    }

    public int getLeftTurnTiming(direction direction) {

        return 1;
    }

    public int getCrossWalkTiming(direction direction) {

        return 1;
    }

    public void shortenCurrentTrafficLightDuration(int timeToShortenBy) {

    }

    public void shortenCurrentCrossWalkDuration(int timeToShortenBy) {

    }

    public void pedestrianInput(direction requestedCrossingDirection) {

    }

    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) {

    }

    /**
     * This function accepts 4 integers and return the optimal number of components
     * for
     * those integers.
     * 
     * @see inputOptimization
     * @param L the number of components for the "left-direction road (direction
     *          one)
     * @param U the number of components for the "up"-direction road (direction two)
     * @param R the number of components for the "right"-direction road (direction
     *          one)
     * @param D the number of components for the "down"-direction road (direction
     *          two)
     * @return The optimized number of components for each road
     */
    private int optimizer(int L, int U, int R, int D) {
        return Math.max(Math.max(L, R), Math.max(U, D));
    }

    /**
     * System admin provides input that specifies the ideal number of components for
     * each direction.
     * 
     * @param input A 3x3 matrix that specifies the expected # of components (Lane,
     *              Left, Right) for each direction.
     * @return Whether the optimization algorithm successfully executed, raises
     *         Exception otherwise.
     */
    public int inputOptimization(Integer[][] input) throws IllegalArgumentException, OverwriteException {
        if (input.length != 3 || input[0].length != 3) {
            throw new IllegalArgumentException("expected 3x3 integer matrix, got non-conforming dimensions");
        } else if (!OVERWRITE_EXISTING_OPTIMIZATION) {
            throw new OverwriteException("illegal operation: attempt to overwrite optimization data");
        }

        int res[] = { 0, 0, 0 };

        for (int i = 0; i < 3; i++) {
            res[i] = optimizer(input[0][i], input[1][i], input[2][i], input[3][i]);
        }

        return 1;
    }

    public String viewOptimizationRecommendation() {

        return "success";
    }

    public int applyOptimization() {

        return 1;
    }

    public int getTimeToCountDownFrom() {
        return currentGreenLightTimer;
    }

}
