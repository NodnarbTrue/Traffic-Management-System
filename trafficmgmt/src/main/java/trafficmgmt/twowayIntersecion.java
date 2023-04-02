package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.entitytype;
import trafficmgmt.utility.intersectionType;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.exceptions.OverwriteException;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;

public class twowayIntersecion extends Intersection {

    // The length of a countdown for when the two way
    // intersection gets a train or padestrian input
    int inputCountDownLength;
    int crosswalkFullLength;
    boolean trainWait;
    int data[];

    /**
     * Constructor method of the two way intersection class
     * 
     * @param intersectionRoadOneName: Name of road
     * @param inputCountDownLength:    The length of time between a train or
     *                                 padestrian signaling and light turning red
     */
    public twowayIntersecion(String intersectionRoadOneName, int inputCountDownLength) {
        super(intersectionRoadOneName);
        this.inputCountDownLength = inputCountDownLength;
        this.intersectionClassification = intersectionType.TWO_WAY;

        trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE);
        trafficlight firstDirectionTrafficlightTwo = new trafficlight(direction.DIRECTION_ONE);
        directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        directionOneTrafficLights.add(firstDirectionTrafficlightTwo);

        crosswalk firstDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_ONE);
        crosswalk firstDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_ONE);
        directionOneCrosswalks.add(firstDirectionCrosswalkOne);
        directionOneCrosswalks.add(firstDirectionCrosswalkTwo);

        crosswalk secondDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_TWO);
        crosswalk secondDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_TWO);
        directionTwoCrosswalks.add(secondDirectionCrosswalkOne);
        directionTwoCrosswalks.add(secondDirectionCrosswalkTwo);

        // The defulat length of the direction with just crosswalks
        this.crosswalkFullLength = 50; // for direction two when they are stopping traffic to cross
        this.trainWait = false;
    }

    public twowayIntersecion(String intersectionRoadOneName, int inputCountDownLength, int crosswalkFullLength) {
        this(intersectionRoadOneName, inputCountDownLength);
        this.crosswalkFullLength = crosswalkFullLength;
    }

    /**
     * Starting the intersection by setting the current direction lights and
     * crosswalk
     */
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

        // Timer Thread start would be here but two way intersections only countdown
        // when they get a signal from a padestrian wanting to cross the
        // opposite direction or if a trian signal is recived

        return 1;
    };

    /**
     * Stopping the intersection by stopping the current direction lights and
     * crosswalk
     * Also stopping the timer thread.
     */
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

        this.intersectionTimer.stop();

        return 1;
    }

    // The following are methods used for information gathering by the timer class

    /**
     * Method for retrival of length informaiton from the current direction's
     * objects
     * 
     * @param infoToReturn: the piece of information requested
     */
    public Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn) {
        switch (infoToReturn) {

            case YELLOW_LIGHT_LENGTH:
                if (this.currentDirection == direction.DIRECTION_ONE) {
                    return this.directionOneTrafficLights.get(0).getLightTiming(lightState.YELLOW);
                }
                break;

            case CROSSWALK_COUTDOWN_LENGTH:
                if (this.currentDirection == direction.DIRECTION_ONE) {
                    return directionOneCrosswalks.get(0).getCrossWalkTiming();
                } else if (this.currentDirection == direction.DIRECTION_TWO) {
                    return directionTwoCrosswalks.get(0).getCrossWalkTiming();
                }
                break;

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
        if (this.currentDirection == direction.DIRECTION_ONE) {
            return this.inputCountDownLength;
        } else if (this.currentDirection == direction.DIRECTION_TWO) {
            return this.crosswalkFullLength;
        } else {
            // SYS ADMIN ERROR
            return this.inputCountDownLength;
        }
    }

    /**
     * Method for retrival of exsistance of left turns in the current direction
     * traffic lights.
     * This method should always return false for the two way intersection class,
     * therefore
     * the if statment is not nessisary. But it is required for the three and four
     * way intersections.
     */
    public boolean getCurrentDirectionLeftTurnExistance() {
        if (this.currentDirection == direction.DIRECTION_ONE) {
            return directionOneTrafficLights.get(0).getLeftTurnExistance();
        }

        return false;
    }

    // The following methods are used for state updating/setting by the timer class

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
            // Implement for 3 and 4 way intersections
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
        if (this.currentDirection == direction.DIRECTION_ONE) {
            for (crosswalk i : directionOneCrosswalks) {
                i.setCurrentCrossWalkTiming(currentTimer);
            }
        }

        else if (this.currentDirection == direction.DIRECTION_TWO) {
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCurrentCrossWalkTiming(currentTimer);
            }
        }
    }

    public void setRoadOneName(String name) {
        this.intersectionRoadOneName = name;
    }

    // Do nothing
    public void setRoadTwoName(String name) {
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
            if (this.trainWait == false) {
                this.intersectionTimer = new timer(this);
                this.intersectionTimer.start();
            }
        } else if (this.currentDirection == direction.DIRECTION_TWO) {
            // this.currentDirection = direction.DIRECTION_ONE;
            this.startIntersection();
        }
    }

    // The following methods are for inputs incoming to this subclass

    /**
     * Method that starts the countdown for crossing against traffic.
     * Nothing occures if pedestrian wants to cross in the direction
     * of traffic because it will eventually happen.
     */
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

    public void trainIncomingSignal() {
        this.trainWait = true;
        this.intersectionTimer = new timer(this);
        this.intersectionTimer.start();
    }

    public void trainClearSignal() {
        this.trainWait = false;
        this.startIntersection();
    }

    /**
     * Method that shortens the timer running for the pedestrians if the current
     * direction
     * is the direction in which pedestrians are crossing
     */
    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) {
        if ((startDirection == direction.DIRECTION_ONE) && this.currentDirection == direction.DIRECTION_TWO)  {
            // attepts to shorten the timer by 5 seconds
            shortenDirectionDuration(5);
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

    // The following methods are for allowing sys admin to get and set crosswalk
    // timing

    /**
     * Method to change the time the coutdown number display starts from
     * 
     * @param direction: direction to change value for
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
     * Method return the time the coutdown number display starts from
     * 
     * @param direction: direction to get the value from
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

    // Debugging Method
    public void printAllStates() {
        String output = new String("");

        output += "Direction One Light States: \n";
        for (trafficlight i : directionOneTrafficLights) {
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

    public String getIntersectionType() {
        return "Two Way Intersection";
    }

    /**
     * This function accepts 2 integers and return the optimal number of components
     * for
     * those integers.
     * 
     * @see inputOptimization
     * @param L the number of components for the "left-direction road (direction
     *          one)
     * @param R the number of components for the "right"-direction road (direction
     *          one)
     * @return The optimized number of components for each road
     */
    private int optimizer(int L, int R) {
        return (int) Math.ceil(Math.log10(Math.max(Math.max(L, R), 1)));
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
        if (input.length != 2 || input[0].length != 3) {
            throw new IllegalArgumentException("Expected 2x3 integer matrix, got non-conforming dimensions");
        } else if (!OVERWRITE_EXISTING_OPTIMIZATION) {
            throw new OverwriteException("Illegal operation: attempt to overwrite optimization data");
        }

        data = new int[3];

        for (int i = 0; i < 2; i++) {
            data[i] = optimizer(input[0][i], input[1][i]);
        }

        if (data[1] > 0 || data[2] > 0) {
            throw new IllegalArgumentException("Two-Way cannot have right/left turn");
        }

        data[0] = Math.max(data[0], 1);

        return 1;
    }

    public String viewOptimizationRecommendation() {
        return "<html>Number of Straight Lanes: " + data[0] + "<br/>Number of Left Turn Lanes: " + data[1]
                + "<br/>Number of Right Turn Lanes:" + data[2] + "<br/>Traffic Light Timing:" + data[0] * 10
                + "<br/>Left Turn Light Timing: " + data[1] * 10 + "<html/>";
    }

    public int applyOptimization() {
        this.inputCountDownLength = data[0] * 10;
        changeCrossWalkTiming(direction.DIRECTION_ONE, data[0] * 10);
        changeCrossWalkTiming(direction.DIRECTION_TWO, data[0] * 10);
        return 1;
    }
}
