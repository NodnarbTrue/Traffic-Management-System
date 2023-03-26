package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.entitytype;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;

public class threewayIntersection extends Intersection {
    //VARIABLES
    // Name of second road
    protected String intersectionRoadTwoName;

    // Seconds left until the current green left light turns red (this variable counts down)
    protected int currentLeftLightTimer; 

    // Length in seconds that the directionTwo timer starts at once it turns green
    protected int directionTwoLightLength; 
    protected int directionOneLeftLightLength; 
    protected int directionTwoLeftLightLength; 

    protected ArrayList<trafficlight> directionTwoTrafficLights;

    public threewayIntersection(
    int directionOneLightLength, int directionTwoLightLength, 
    int directionOneLeftLightLength, String intersectionRoadOneName, String intersectionRoadTwoName) {

        super(directionOneLightLength, intersectionRoadOneName);
        this.directionTwoTrafficLights = new ArrayList<trafficlight>();
        this.directionTwoLightLength = directionTwoLightLength;
        this.intersectionRoadTwoName = intersectionRoadTwoName;
        this.directionOneLeftLightLength = directionOneLeftLightLength;

        //Assumes that intersection always starts with Direction_One being green
        this.currentDirection = direction.DIRECTION_ONE;

        // Assumes firstDirectionTrafficlight is the road that can turn left
        trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE, directionOneLightLength, directionOneLightLength);
        trafficlight firstDirectionTrafficlightTwo = new trafficlight(direction.DIRECTION_TWO, directionOneLightLength);

        trafficlight directionTwoLight = new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength);
        directionTwoTrafficLights.add(directionTwoLight);

        directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        directionOneTrafficLights.add(firstDirectionTrafficlightTwo);

        crosswalk directionOneCrosswalk = new crosswalk(direction.DIRECTION_ONE);
        directionOneCrosswalks.add(directionOneCrosswalk);

        crosswalk secondDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_TWO);
        crosswalk secondDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_TWO);
        directionTwoCrosswalks.add(secondDirectionCrosswalkOne);
        directionTwoCrosswalks.add(secondDirectionCrosswalkTwo);
    }

    public int startIntersection() {
        if (currentDirection == direction.DIRECTION_ONE){
            for (trafficlight i : directionTwoTrafficLights) {
                i.turnRed();
            }
            for (crosswalk i : directionTwoCrosswalks) {
                i.stopSignal();
            }
            for (trafficlight i : directionOneTrafficLights) {
                i.turnGreen();
            }
            for (crosswalk i : directionOneCrosswalks) {
                i.walkSignal();
            }
        }
        else{
            for (trafficlight i : directionOneTrafficLights) {
                i.turnRed();
            }
            for (crosswalk i : directionOneCrosswalks) {
                i.stopSignal();
            }
            for (trafficlight i : directionTwoTrafficLights) {
                i.turnGreen();
            }
            for (crosswalk i : directionTwoCrosswalks) {
                i.walkSignal();
            }
        }

        this.intersectionTimer = new timer(this);
        this.intersectionTimer.run();

        return 1;
    };

    public int stopIntersection() {

        for (trafficlight i : directionOneTrafficLights) {
            i.turnRed();
        }

        for (trafficlight i : directionTwoTrafficLights) {
            i.turnRed();
        }

        for (crosswalk i : directionOneCrosswalks) {
            i.stopSignal();
        }
        for (crosswalk i : directionTwoCrosswalks) {
            i.stopSignal();
        }

        this.intersectionTimer.stop();

        return 1;
    }

    //Methods used for information gathering by the timer class
    public boolean getCurrentDirectionLeftTurnExistance(){
        if (this.currentDirection == direction.DIRECTION_ONE){
            return directionOneTrafficLights.get(0).getLeftTurnExistance();
        }
        else{
            return directionTwoTrafficLights.get(0).getLeftTurnExistance();
        }
    }

    public Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn) { 
        switch (infoToReturn) {

            case Direction_LENGTH:
                return this.getTrafficLightTiming(currentDirection);

            case LEFT_TURN_LENGTH:
                return this.getLeftTurnTiming(currentDirection);

            case YELLOW_LIGHT_LENGTH:
                if (this.currentDirection == direction.DIRECTION_ONE) { 
                    return this.directionOneTrafficLights.get(0).getLightTiming(lightState.YELLOW);
                }
                else if (this.currentDirection == direction.DIRECTION_TWO) {
                    return this.directionTwoTrafficLights.get(0).getLightTiming(lightState.YELLOW);
                }
                break;
            
            case CROSSWALK_COUTDOWN_LENGTH:
                this.getCrossWalkTiming(currentDirection);
            
            default:
                break;
        } 
        return 1;
    }

    /**
     * Method for retrival of the time the timer should start to 
     * count down from.
     */
    public int getTimeToCountDownFrom() { 
        return this.getTrafficLightTiming(currentDirection);
    }

    // The following methods are used for state updating/setting by the timer class

    /**
     * Method for changing the state of all traffic lights in the current direction
     * @param newState: the state to set all the traffic lights in the current direction to
     */
    public void setAllCurrentDirectionTrafficLights(lightState newState) { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightState(newState);
            }
        } else if (currentDirection == direction.DIRECTION_TWO) { 
            directionTwoTrafficLights.get(0).setLightState(newState);
        }
    }

    /**
     * Method for changing the state of all crosswalks in the current direction
     * @param newState: the state to set all the crosswalks in the current direction to
     */
    public void setAllCurrentDirectionCrosswalk(crosswalkState newState) { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (crosswalk i : directionOneCrosswalks) { 
                i.setCrossWalkState(newState);
            }
        } else if (currentDirection == direction.DIRECTION_TWO) { 
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCrossWalkState(newState);
            }
        }
    }

    /**
     * Method for decrementing the timer number inside each crosswalk in 
     * the current direction
     */
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

    /**
     * Method for switching the direction of the current instance 
     * of the intersection class.
     */
    public void switchDirection() { 
        if (this.currentDirection == direction.DIRECTION_ONE) { 
            this.currentDirection = direction.DIRECTION_TWO;
            // the timer counted down and switched the directions
            // so need to count down for the other direction's crosswalk
            this.intersectionTimer = new timer(this);
            this.intersectionTimer.start();

        } else if (this.currentDirection == direction.DIRECTION_TWO) { 
            this.currentDirection = direction.DIRECTION_ONE;
            this.intersectionTimer = new timer(this);
            this.intersectionTimer.start();
        }
    }

    //Functions for accessing/modifying lights and crosswalks
    public void changeTrafficLightTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightLength(lightState.GREEN, newLength);
            }
        } 
        else if (dir == direction.DIRECTION_TWO){
            directionTwoTrafficLights.get(0).setLightLength(lightState.GREEN, newLength);
        }
    }

    public void changeLeftTurnTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE){
            directionTwoTrafficLights.get(0).setLightLength(lightState.LEFT_TURN, newLength);
        }
        else if (dir == direction.DIRECTION_TWO){
            directionTwoTrafficLights.get(0).setLightLength(lightState.LEFT_TURN, newLength);
        }
    }

    public void changeCrossWalkTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_TWO) {
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCrossWalkTiming(newLength);
            }
        } 
        else if (dir == direction.DIRECTION_TWO){
            directionOneCrosswalks.get(0).setCrossWalkTiming(newLength);
        }
    }

    public int getTrafficLightTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE){
            return directionOneTrafficLights.get(0).getLightTiming(lightState.GREEN);
        }
        else if (dir == direction.DIRECTION_TWO){
            return directionTwoTrafficLights.get(0).getLightTiming(lightState.GREEN);
        }
        else{
            // SYS ADMIN ERROR
            return 0;
        }
    }

    public int getLeftTurnTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE){
            return directionOneTrafficLights.get(0).getLightTiming(lightState.LEFT_TURN);
        }
        else if (dir == direction.DIRECTION_TWO){
            return directionTwoTrafficLights.get(0).getLightTiming(lightState.LEFT_TURN);
        }
        else{
            // SYS ADMIN ERROR
            return 0;
        }
    }

    public int getCrossWalkTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE){
            return directionOneCrosswalks.get(0).getCrossWalkTiming();
        }
        else if (dir == direction.DIRECTION_TWO){
            return directionTwoCrosswalks.get(0).getCrossWalkTiming();
        }
        else{
            // SYS ADMIN ERROR
            return 0;
        }
    }
    // Debugging Method
    public void printAllStates() { 
        String output = new String("");
        
        output += "Direction One Light States: \n";
        for (trafficlight i : directionOneTrafficLights) {
            output += i.getCurrentLightState() + "\t";
        }

        output += "\nDirection Two Light States: \n";
        for (trafficlight i : directionTwoTrafficLights) {
            output += i.getCurrentLightState() + "\t";
        }

        output += "\nDirection One Crosswalk States: \n";
        for (crosswalk i : directionOneCrosswalks) { 
            output += i.getCurrentCrossWalkState() + " " + i.getCurrentCrossWalkTiming() + "\t";
        }
        
        output += "\n Direction Two Crosswalk States: \n";
        for (crosswalk i : directionTwoCrosswalks) {
            output += i.getCurrentCrossWalkState() + " " + i.getCurrentCrossWalkTiming() + "\t";
        }

        System.out.println(output);
    }

    //INPUTS
    public void pedestrianInput(direction requestedCrossingDirection) {
        if (requestedCrossingDirection != currentDirection){
            this.shortenDirectionDuration(10);
        }
    }

    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) {
        if (crossingDirection != currentDirection){
            this.shortenDirectionDuration(2);
        }
    }

    public void shortenDirectionDuration(int timeToShortenBy) {
        this.intersectionTimer.shortenCountDownTimer(timeToShortenBy);
    }

    //OPTIMIZATION
    public int inputOptimization(Integer[][] input) {

        return 1;
    }

    public String viewOptimizationRecommendation() {

        return "success";
    }

    public int applyOptimization() {

        return 1;
    }
}
