package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;

import trafficmgmt.exceptions.OverwriteException;

public class fourwayIntersection extends Intersection {

    // VARIABLES

    protected String intersectionRoadTwoName;

    protected int currentLeftLightTimer; // Seconds left until the current green left light turns red (this variable
                                         // counts down)

    protected int directionTwoLightLength; // Length in seconds that the directionTwo timer starts at once it turns
                                           // green
    protected int directionOneLeftLightLength; // Length in seconds that the directionOne left light timer starts at
                                               // once it turns green
    protected int directionTwoLeftLightLength; // Length in seconds that the directionTwo left light timer starts at
                                               // once it turns green

    protected ArrayList<trafficlight> directionTwoTrafficLights;

    // CONSTRUCTOR

    public fourwayIntersection(int directionOneLightLength, int directionTwoLightLength, String intersectionRoadOneName,
            String intersectionRoadTwoName) {
        super(directionOneLightLength, intersectionRoadOneName);
        this.directionTwoLightLength = directionTwoLightLength;
        this.intersectionRoadTwoName = intersectionRoadTwoName;

        // ??????????????????????????????????????
        trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE, directionOneLightLength);
        trafficlight firstDirectionTrafficlightTwo = new trafficlight(direction.DIRECTION_ONE, directionOneLightLength);
        directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        directionOneTrafficLights.add(firstDirectionTrafficlightTwo);

        crosswalk firstDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_ONE);
        crosswalk firstDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_ONE);
        directionOneCrosswalks.add(firstDirectionCrosswalkOne);
        directionOneCrosswalks.add(firstDirectionCrosswalkTwo);

        crosswalk secondDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_TWO);
        crosswalk secondDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_TWO);
        directionOneCrosswalks.add(secondDirectionCrosswalkOne);
        directionOneCrosswalks.add(secondDirectionCrosswalkTwo);
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
