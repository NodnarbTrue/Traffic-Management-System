package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.intersectionType;
import trafficmgmt.utility.lightState;
import trafficmgmt.exceptions.OverwriteException;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;

public class threewayIntersection extends Intersection {
    // VARIABLES
    // Name of second road
    protected String intersectionRoadTwoName;

    // Seconds left until the current green left light turns red (this variable
    // counts down)
    protected int currentLeftLightTimer;

    // Length in seconds that the directionTwo timer starts at once it turns green
    protected int directionTwoLightLength;
    protected int directionOneLeftLightLength;

    // public ArrayList<trafficlight> directionTwoTrafficLights;

    private int data[];

    public threewayIntersection(
            int directionOneLightLength, int directionTwoLightLength,
            int directionOneLeftLightLength, String intersectionRoadOneName, String intersectionRoadTwoName) {

        super(directionOneLightLength, intersectionRoadOneName);
        this.directionTwoTrafficLights = new ArrayList<trafficlight>();
        this.directionTwoLightLength = directionTwoLightLength;
        this.intersectionRoadTwoName = intersectionRoadTwoName;
        this.directionOneLeftLightLength = directionOneLeftLightLength;
        this.intersectionClassification = intersectionType.THREE_WAY;

        // Assumes that intersection always starts with Direction_One being green
        this.currentDirection = direction.DIRECTION_ONE;

        // Assumes firstDirectionTrafficlight is the road that can turn left
        if (directionOneLeftLightLength == 0) {
            trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE,
                    directionOneLightLength);
            this.directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        } else {
            trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE,
                    directionOneLightLength, directionOneLeftLightLength);
            this.directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        }

        trafficlight firstDirectionTrafficlightTwo = new trafficlight(direction.DIRECTION_ONE, directionOneLightLength);
        this.directionOneTrafficLights.add(firstDirectionTrafficlightTwo);

        trafficlight directionTwoLight = new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength); // green
                                                                                                             // indicates
                                                                                                             // left
                                                                                                             // turn
        this.directionTwoTrafficLights.add(directionTwoLight);

        crosswalk directionOneCrosswalk = new crosswalk(direction.DIRECTION_ONE);
        this.directionOneCrosswalks.add(directionOneCrosswalk);

        crosswalk secondDirectionCrosswalkOne = new crosswalk(direction.DIRECTION_TWO);
        crosswalk secondDirectionCrosswalkTwo = new crosswalk(direction.DIRECTION_TWO);
        this.directionTwoCrosswalks.add(secondDirectionCrosswalkOne);
        this.directionTwoCrosswalks.add(secondDirectionCrosswalkTwo);
    }

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
        } else {
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

    // Methods used for information gathering by the timer class
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
     * Method for retrival of the time the timer should start to
     * count down from.
     */
    public int getTimeToCountDownFrom() {
        return this.getTrafficLightTiming(this.currentDirection);
    }

    public boolean getCurrentDirectionLeftTurnExistance() {
        if (this.currentDirection == direction.DIRECTION_ONE) {
            return directionOneTrafficLights.get(0).getLeftTurnExistance();
        } else {
            return directionTwoTrafficLights.get(0).getLeftTurnExistance();
        }
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
            if (newState == lightState.LEFT_TURN) {
                directionOneTrafficLights.get(0).setLightState(newState);
                directionOneTrafficLights.get(1).setLightState(lightState.RED);
            } else {
                for (trafficlight i : directionOneTrafficLights) {
                    i.setLightState(newState);
                }
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
        if (this.currentDirection == direction.DIRECTION_ONE) {
            for (crosswalk i : directionOneCrosswalks) {
                i.setCrossWalkState(newState);
            }
        } else if (this.currentDirection == direction.DIRECTION_TWO) {
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

    // INPUTS
    public void pedestrianInput(direction requestedCrossingDirection) {
        if (requestedCrossingDirection != currentDirection) {
            this.shortenDirectionDuration(5);
        }
    }

    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) {
        if (crossingDirection != currentDirection) {
            this.shortenDirectionDuration(5);
        }
    }

    public void shortenDirectionDuration(int timeToShortenBy) {
        this.intersectionTimer.shortenCountDownTimer(timeToShortenBy);
    }

    // Functions for accessing/modifying lights and crosswalks

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

    public int getCrossWalkTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE) {
            return this.directionOneCrosswalks.get(0).getCrossWalkTiming();
        } else if (dir == direction.DIRECTION_TWO) {
            return this.directionTwoCrosswalks.get(0).getCrossWalkTiming();
        } else {
            // SYS ADMIN ERROR
            return 0;
        }
    }

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

    public int getTrafficLightTiming(direction dir) {
        if (dir == direction.DIRECTION_ONE) {
            return this.directionOneTrafficLights.get(0).getLightTiming(lightState.GREEN);
        } else if (dir == direction.DIRECTION_TWO) {
            return this.directionTwoTrafficLights.get(0).getLightTiming(lightState.GREEN);
        } else {
            // SYS ADMIN ERROR
            return 0;
        }
    }

    public void setRoadOneName(String name) {
        this.intersectionRoadOneName = name;
    }

    public void setRoadTwoName(String name) {
        this.intersectionRoadTwoName = name;
    }

    // This would not apply to direction 2 as there would never be a left turn
    // lane/signal in that direction
    public void changeLeftTurnTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            directionOneTrafficLights.get(0).setLightLength(lightState.LEFT_TURN, newLength);
        }
    }

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
            output += i.getCurrentCrossWalkState() + " " + i.currentcrosswalkCountDownNumber + "\t";
        }

        output += "\n Direction Two Crosswalk States: \n";
        for (crosswalk i : directionTwoCrosswalks) {
            output += i.getCurrentCrossWalkState() + " " + i.currentcrosswalkCountDownNumber + "\t";
        }

        System.out.println(output);
    }

    public String getIntersectionType() {
        return "Three Way Intersection";
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
     * @return The optimized number of components for each road
     */
    private int optimizer(int L, int U, int R) {
        return (int) Math.ceil(Math.log10(Math.max(Math.max(L, U), Math.max(R, 2))));
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
            throw new IllegalArgumentException("Expected 3x3 integer matrix, got non-conforming dimensions");
        } else if (!OVERWRITE_EXISTING_OPTIMIZATION) {
            throw new OverwriteException("Illegal operation: attempt to overwrite optimization data");
        }

        data = new int[3];

        for (int i = 0; i < 3; i++) {
            data[i] = optimizer(input[0][i], input[1][i], input[2][i]);
        }

        if (input[0][1] > 0 || input[1][1] > 0) {
            throw new IllegalArgumentException("Long road cannot have left turns.");
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
