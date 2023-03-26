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

            // WHAT IF CROSSWALK TIME IS GREATER THAN LIGHT LENGTH?
            // directionOne has 2 crosswalk objects
            directionOneCrosswalks.add(new crosswalk(direction.DIRECTION_ONE));
            // directionTwo has 2 crosswalk objects
            directionTwoCrosswalks.add(new crosswalk(direction.DIRECTION_TWO));
        }   
    }

    // METHODS

    public int startIntersection() {
        return 1;
    }


    public int stopIntersection() {
        return 1;
    }


    public boolean getCurrentDirectionLeftTurnExistance() {
        if (this.currentDirection == direction.DIRECTION_ONE) { 
            return directionOneTrafficLights.get(0).getLeftTurnExistance(); }
        else if (this.currentDirection == direction.DIRECTION_TWO) {
            return directionTwoTrafficLights.get(0).getLeftTurnExistance(); }
        return false;
    }


    public Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn) { 
        return 1;
    }


    public void setAllCurrentDirectionTrafficLights(lightState newState) { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightState(newState); } } 
        else if (currentDirection == direction.DIRECTION_TWO) { 
            for (trafficlight i : directionTwoTrafficLights) {
                i.setLightState(newState); } } 
    }
    

    public void setAllCurrentDirectionCrosswalk(crosswalkState newState) { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (crosswalk i : directionOneCrosswalks) { 
                i.setCrossWalkState(newState); } } 
        else if (currentDirection == direction.DIRECTION_TWO) { 
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCrossWalkState(newState); } }
    }


    public void setCurrentDirectionCrossWalkTimer(int currentTimer) { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (crosswalk i : directionOneCrosswalks) { 
                i.setCurrentCrossWalkTiming(currentTimer);
            }
        } else if (currentDirection == direction.DIRECTION_TWO) { 
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCurrentCrossWalkTiming(currentTimer);
            }
        }
    }

    // ????????
    public void switchDirection() { 
        if (this.currentDirection == direction.DIRECTION_ONE) { 
            this.currentDirection = direction.DIRECTION_TWO;
            // the timer counted down and switched the directions
            // so need to count down for the other direction's crosswalk
            if (this.trainWait == false) { 
                this.intersectionTimer = new timer(this);
                this.intersectionTimer.start();
            } } 
        else if (this.currentDirection == direction.DIRECTION_TWO) { 
            this.currentDirection = direction.DIRECTION_ONE;
            this.startIntersection();
        }
    }

    // ?????????
    public void pedestrianInput(direction requestedCrossingDirection) { 
        switch (requestedCrossingDirection) {
            case DIRECTION_ONE:
                if (currentDirection != direction.DIRECTION_ONE) {
                    // Do nothing since if it's the second direction then 
                    // it's already counting down
                }
                break;
            
            case DIRECTION_TWO:
                if (currentDirection == direction.DIRECTION_ONE) { 
                    this.intersectionTimer = new timer(this);
                    this.intersectionTimer.start();
                }
                break;
            
            default:
                break;
        } 

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
