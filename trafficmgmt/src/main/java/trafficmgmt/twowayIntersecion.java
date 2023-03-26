package trafficmgmt;
import java.util.ArrayList;
import trafficmgmt.utility.entitytype;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;

public class twowayIntersecion extends Intersection{
    
    // The length of a countdown for when the two way  
    // intersection gets a train or padestrian input
    int inputCountDownLength; 
    int crosswalkFullLength;
    boolean trainWait;

    /**
     * Constructor method of the two way intersection class
     * @param intersectionRoadOneName: Name of road
     * @param inputCountDownLength: The length of time between a train or padestrian signaling and light turning red
     */
    public twowayIntersecion(String intersectionRoadOneName, int inputCountDownLength) {
        super(intersectionRoadOneName);
        this.inputCountDownLength = inputCountDownLength;

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
        this.crosswalkFullLength = 50;
        this.trainWait = false;
    }

    public twowayIntersecion(String intersectionRoadOneName, int inputCountDownLength, int crosswalkFullLength) {
        this(intersectionRoadOneName, inputCountDownLength);
        this.crosswalkFullLength = crosswalkFullLength;
    }





    /**
     * Starting the intersection by setting the current direction lights and crosswalk
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
     * Stopping the intersection by stopping the current direction lights and crosswalk
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
     * Method for retrival of length informaiton from the current direction's objects
     * @param infoToReturn: the piece of information requested
     */
    public Integer getLengthInformationFromCurrentDirection(timerlengthinformation infoToReturn) { 
        switch (infoToReturn) {

            case YELLOW_LIGHT_LENGTH:
                if (this.currentDirection == direction.DIRECTION_ONE) { 
                    return this.directionOneLightLength;
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
     * Method for retrival of exsistance of left turns in the current direction traffic lights.
     * This method should always return false for the two way intersection class, therefore 
     * the if statment is not nessisary. But it is required for the three and four way intersections.
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
     * @param newState: the state to set all the traffic lights in the current direction to
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
            if (this.trainWait == false) { 
                this.intersectionTimer = new timer(this);
                this.intersectionTimer.start();
            }
        } else if (this.currentDirection == direction.DIRECTION_TWO) { 
            //this.currentDirection = direction.DIRECTION_ONE;
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
     * Method that shortens the timer running for the pedestrians if the current direction 
     * is the direction in which pedestrians are crossing
     */
    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) { 
        if ((((startDirection == direction.DIRECTION_ONE) && (crossingDirection == direction.DIRECTION_TWO)) || 
            ((startDirection == direction.NORTH) && (crossingDirection == direction.SOUTH)) || 
            ((startDirection == direction.SOUTH) && (crossingDirection == direction.NORTH)) || 
            ((startDirection == direction.EAST) && (crossingDirection == direction.WEST)) || 
            ((startDirection == direction.WEST) && (crossingDirection == direction.EAST))) &&
            this.currentDirection == direction.DIRECTION_TWO) { 
                // attepts to shorten the timer by 5 seconds
                shortenDirectionDuration(5);
        }
    }

    /**
     * Method that attmepts to shorten the currently running timer
     * @param timeToShortenBy: an integer that is passed to timer
     */
    public void shortenDirectionDuration(int timeToShortenBy) { 
        this.intersectionTimer.shortenCountDownTimer(timeToShortenBy);
    }





    // The following methods are for allowing sys admin to get and set crosswalk timing

    /**
     * Method to change the time the coutdown number display starts from
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




    // The following methods are for optimization

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
