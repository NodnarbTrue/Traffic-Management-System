package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.intersectionType;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class fourwayIntersection extends Intersection {

    // VARIABLES

    // Name of second road
    protected String intersectionRoadTwoName;

    // Seconds left until the current green left light turns red (this variable
    // counts down)
    protected int currentLeftLightTimer;

    // Length in seconds that the directionTwo timer starts at once it turns green
    protected int directionTwoLightLength;
    protected int directionOneLeftLightLength;
    protected int directionTwoLeftLightLength;

    // List of trafficlight objects
    // public ArrayList<trafficlight> directionTwoTrafficLights;
    int data[];

    /**
     * Constructor method of the four way intersection class
     * 
     * @param directionOneLightLength:     Length of directionOne's green light
     * @param directionTwoLightLength:     Length of directionTwo's green light
     * @param directionOneLeftLightLength: Length of directionOne's left green light
     * @param directionTwoLeftLightLength: Length of directionTwo's left green light
     * @param intersectionRoadOneName:     Name of directionOne's road
     * @param intersectionRoadTwoName:     Name of directionTwo's road
     */
    public fourwayIntersection(
            int directionOneLightLength, int directionTwoLightLength,
            int directionOneLeftLightLength, int directionTwoLeftLightLength,
            String intersectionRoadOneName, String intersectionRoadTwoName) {

        super(directionOneLightLength, intersectionRoadOneName);
        this.directionTwoTrafficLights = new ArrayList<trafficlight>();
        this.directionTwoLightLength = directionTwoLightLength;
        this.intersectionRoadTwoName = intersectionRoadTwoName;
        this.directionOneLeftLightLength = directionOneLeftLightLength;
        this.directionTwoLeftLightLength = directionTwoLeftLightLength;
        this.intersectionClassification = intersectionType.FOUR_WAY;

        // Assumes that intersection always starts with Direction_One being green
        this.currentDirection = direction.DIRECTION_ONE;

        // Loop twice since there are 2 trafficlight and crosswalk objects for each
        // direction
        for (int i = 1; i <= 2; i++) {
            // directionOne has 2 trafficlight objects, if left light length is 0, no left
            // turn signal
            if (directionOneLeftLightLength == 0) {
                this.directionOneTrafficLights.add(new trafficlight(direction.DIRECTION_ONE, directionOneLightLength));
            } else {
                this.directionOneTrafficLights.add(new trafficlight(direction.DIRECTION_ONE, directionOneLightLength,
                        directionOneLeftLightLength));
            }

            // directionTwo has 2 trafficlight objects, if left light length is 0, no left
            // turn signal
            if (directionTwoLeftLightLength == 0) {
                this.directionTwoTrafficLights.add(new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength));
            } else {
                this.directionTwoTrafficLights.add(new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength,
                        directionTwoLeftLightLength));
            }

            // directionOne has 2 crosswalk objects
            this.directionOneCrosswalks.add(new crosswalk(direction.DIRECTION_ONE));
            // directionTwo has 2 crosswalk objects
            this.directionTwoCrosswalks.add(new crosswalk(direction.DIRECTION_TWO));
        }
    }

    // METHODS

    /**
     * Starting the intersection by setting the current direction lights and
     * crosswalk
     */
    public int startIntersection() {
        if (currentDirection == direction.DIRECTION_ONE) {
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
        } else if (currentDirection == direction.DIRECTION_TWO) {
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
        this.intersectionTimer.start();
        return 1;
    }

    /**
     * Stopping the intersection by stopping the current direction lights and
     * crosswalk
     * Also stopping the timer thread.
     */
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

    /**
     * Method to check if a trafficlight object has a left turn signal
     */
    public boolean getCurrentDirectionLeftTurnExistance() {
        if (this.currentDirection == direction.DIRECTION_ONE) {
            return directionOneTrafficLights.get(0).getLeftTurnExistance();
        } else if (this.currentDirection == direction.DIRECTION_TWO) {
            return directionTwoTrafficLights.get(0).getLeftTurnExistance();
        }
        return false;
    }

    /**
     * Method for retrival of length informaiton from the current direction's
     * objects
     * 
     * @param infoToReturn: the piece of information requested
     */
    public Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn) {
        switch (infoToReturn) {
            case Direction_LENGTH:
                return this.getTrafficLightTiming(currentDirection);
            case LEFT_TURN_LENGTH:
                return this.getLeftTurnTiming(currentDirection);
            case YELLOW_LIGHT_LENGTH:
                if (this.currentDirection == direction.DIRECTION_ONE) {
                    return this.directionOneTrafficLights.get(0).getLightTiming(lightState.YELLOW);
                } else if (this.currentDirection == direction.DIRECTION_TWO) {
                    return this.directionTwoTrafficLights.get(0).getLightTiming(lightState.YELLOW);
                }
                break;
            case CROSSWALK_COUTDOWN_LENGTH:
                return this.getCrossWalkTiming(currentDirection);
            default:
                break;
        }
        return 1;
    }

    /**
     * Method for changing the state of all traffic lights in the current direction
     * 
     * @param newState: the state to set all the traffic lights in the current
     *                  direction to
     */
    public void setAllCurrentDirectionTrafficLights(lightState newState) {
        if (currentDirection == direction.DIRECTION_ONE) {
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightState(newState);
            }
        } else if (currentDirection == direction.DIRECTION_TWO) {
            for (trafficlight i : directionTwoTrafficLights) {
                i.setLightState(newState);
            }
        }
    }

    /**
     * Method for changing the state of all crosswalks in the current direction
     * 
     * @param newState: the state to set all the crosswalks in the current direction
     *                  to
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

    public void setRoadOneName(String name) {
        this.intersectionRoadOneName = name;
    }

    public void setRoadTwoName(String name) {
        this.intersectionRoadTwoName = name;
    }

    /**
     * Method for switching the direction of the current instance
     * of the intersection class
     */
    public void switchDirection() {
        if (this.currentDirection == direction.DIRECTION_ONE) {
            this.currentDirection = direction.DIRECTION_TWO;
            this.intersectionTimer = new timer(this);
            this.intersectionTimer.start();
        } else if (this.currentDirection == direction.DIRECTION_TWO) {
            this.currentDirection = direction.DIRECTION_ONE;
            this.intersectionTimer = new timer(this);
            this.intersectionTimer.start();
        }
    }

    // INPUTS

    /**
     * Method that starts the countdown for crossing against traffic.
     * Nothing occurs if pedestrian wants to cross in the direction
     * of traffic because it will eventually happen
     * 
     * @param requestedCrossingDirection: the direction the pedestrian wants to
     *                                    cross
     */
    public void pedestrianInput(direction requestedCrossingDirection) {
        if (requestedCrossingDirection != currentDirection) {
            this.shortenDirectionDuration(5);
        }
    }

    /**
     * Method that shortens the timer running for the pedestrians if the current
     * direction
     * is the direction in which pedestrians are crossing
     * 
     * @param startDirection:    the direction the car starts in
     * @param crossingDirection: the direction the car goes
     * @param weight:            how much the car affects the time
     */
    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) {
        if (startDirection != currentDirection) {
            this.shortenDirectionDuration(5);
        }
    }

    /**
     * Method that attmepts to shorten the currently running timer
     * 
     * @param timeToShortenBy: an integer that is passed to timer
     */
    public void shortenDirectionDuration(int timeToShortenBy) {
        this.intersectionTimer.shortenCountDownTimer(timeToShortenBy);
    }

    // SETTERS AND GETTERS

    /**
     * Method for retrival of the time the timer should start to
     * count down from
     */
    public int getTimeToCountDownFrom() {
        return this.getTrafficLightTiming(currentDirection);
    }

    /**
     * Method to return the time the coutdown number display starts from
     * 
     * @param dir: direction to get the value from
     */
    public int getCrossWalkTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE) {
            return directionOneCrosswalks.get(0).getCrossWalkTiming();
        } else if (dir == direction.DIRECTION_TWO) {
            return directionTwoCrosswalks.get(0).getCrossWalkTiming();
        } else {
            // SYS ADMIN ERROR
            return 0;
        }
    }

    /**
     * Method to return the time the length of the current green light
     * 
     * @param dir: direction to get the value from
     */
    public int getTrafficLightTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE) {
            return directionOneTrafficLights.get(0).getLightTiming(lightState.GREEN);
        } else if (dir == direction.DIRECTION_TWO) {
            return directionTwoTrafficLights.get(0).getLightTiming(lightState.GREEN);
        } else {
            // SYS ADMIN ERROR
            return 0;
        }
    }

    /**
     * Method to return the time the length of the current left green light
     * 
     * @param dir: direction to get the value from
     */
    public int getLeftTurnTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE) {
            return directionOneTrafficLights.get(0).getLightTiming(lightState.LEFT_TURN);
        } else if (dir == direction.DIRECTION_TWO) {
            return directionTwoTrafficLights.get(0).getLightTiming(lightState.LEFT_TURN);
        } else {
            // SYS ADMIN ERROR
            return 0;
        }
    }

    /**
     * Method to change the time the coutdown number display starts from
     * 
     * @param dir:       direction to change value for
     * @param newLength: the length to update the value
     */
    public void changeCrossWalkTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            for (crosswalk i : directionOneCrosswalks) {
                i.setCrossWalkTiming(newLength);
            }
        } else if (dir == direction.DIRECTION_TWO) {
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCrossWalkTiming(newLength);
            }
        }
    }

    /**
     * Method to change the time of current trafficlight green light length
     * 
     * @param dir:       direction to change value for
     * @param newLength: the length to update the value
     */
    public void changeTrafficLightTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightLength(lightState.GREEN, newLength);
            }
        } else if (dir == direction.DIRECTION_TWO) {
            for (trafficlight i : directionTwoTrafficLights) {
                i.setLightLength(lightState.GREEN, newLength);
            }
        }
    }

    /**
     * Method to change the time of current trafficlight left green light length
     * 
     * @param dir:       direction to change value for
     * @param newLength: the length to update the value
     */
    public void changeLeftTurnTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            for (trafficlight i : directionOneTrafficLights) {
                i.setLightLength(lightState.LEFT_TURN, newLength);
            }
        } else if (dir == direction.DIRECTION_TWO) {
            for (trafficlight i : directionTwoTrafficLights) {
                i.setLightLength(lightState.LEFT_TURN, newLength);
            }
        }
    }

    // For testing/debugging
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
            output += i.getCurrentCrossWalkState() + " " + i.currentcrosswalkCountDownNumber + "\t";
        }

        output += "\nDirection Two Crosswalk States: \n";
        for (crosswalk i : directionTwoCrosswalks) {
            output += i.getCurrentCrossWalkState() + " " + i.currentcrosswalkCountDownNumber + "\t";
        }

        System.out.println(output);
    }

    public String getIntersectionType() {
        return "Four Way Intersection";
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
        return (int) Math.ceil(Math.log10(Math.max(Math.max(Math.max(L, U), Math.max(R, D)), 1)));
    }

    /**
     * System admin provides input that specifies the ideal number of components for
     * each direction.
     * 
     * @param input A 4x3 matrix that specifies the expected # of components (Lane,
     *              Left, Right) for each direction.
     * @return Whether the optimization algorithm successfully executed, raises
     *         Exception otherwise.
     */
    public int inputOptimization(Integer[][] input) throws IllegalArgumentException, OverwriteException {
        if (input.length != 4 || input[0].length != 3) {
            throw new IllegalArgumentException("Expected 4x3 integer matrix, got non-conforming dimensions");
        } else if (!OVERWRITE_EXISTING_OPTIMIZATION) {
            throw new OverwriteException("Illegal operation: attempt to overwrite optimization data");
        }

        data = new int[3];

        for (int i = 0; i < 3; i++) {
            data[i] = optimizer(input[0][i], input[1][i], input[2][i], input[3][i]);
        }

        return 1;
    }

    public String viewOptimizationRecommendation() {
        return "<html>Number of Straight Lanes: " + data[0] + "<br/>Number of Left Turn Lanes: " + data[1]
                + "<br/>Number of Right Turn Lanes:" + data[2] + "<br/>Traffic Light Timing:" + data[0] * 10
                + "<br/>Left Turn Light Timing: " + data[1] * 10 + "<html/>";
    }

    public int applyOptimization() {
        changeTrafficLightTiming(direction.DIRECTION_ONE, data[0] * 10);
        changeTrafficLightTiming(direction.DIRECTION_TWO, data[0] * 10);
        changeCrossWalkTiming(direction.DIRECTION_ONE, data[0] * 10);
        changeCrossWalkTiming(direction.DIRECTION_TWO, data[0] * 10);
        changeLeftTurnTiming(direction.DIRECTION_ONE, data[1] * 5);
        return 1;
    }
}
