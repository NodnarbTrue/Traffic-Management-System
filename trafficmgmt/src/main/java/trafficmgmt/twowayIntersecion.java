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


    /**
     * Constructor method of the two way intersection class
     * @param intersectionRoadOneName: Name of road
     * @param inputCountDownLength: The length of time between a train or padestrian signaling and light turning red
     */
    public twowayIntersecion(String intersectionRoadOneName, int inputCountDownLength) {
        super(intersectionRoadOneName);
        this.inputCountDownLength = inputCountDownLength;
        this.intersectionTimer = new timer(this);

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
        directionOneCrosswalks.add(secondDirectionCrosswalkOne);
        directionOneCrosswalks.add(secondDirectionCrosswalkTwo);
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

        return null;
    }

    /**
     * Method for retrival of the time the timer should start to 
     * count down from.
     */
    public int getTimeToCountDownFrom() { 
        return this.inputCountDownLength;
    }

    /**
     * Method for retrival of exsistance of left turns in the current direction traffic lights.
     * This method should always return false for the two way intersection class, therefore 
     * the if statment is not nessisary. But it is required for the three and four way intersections.
     */
    public boolean getCurrentDirectionLeftTurnExsistance() { 
        if (this.currentDirection == direction.DIRECTION_ONE) { 
            return directionOneTrafficLights.get(0).getLeftTurnExsistance();
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
    public void decrementCurrentDirectionCrossWalkTimer() { 
        if (currentDirection == direction.DIRECTION_ONE) { 
            for (crosswalk i : directionOneCrosswalks) { 
                i.decrementTimer();
            }
        } else if (currentDirection == direction.DIRECTION_TWO) { 
            for (crosswalk i : directionTwoCrosswalks) {
                i.decrementTimer();
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
        } else if (this.currentDirection == direction.DIRECTION_TWO) { 
            this.currentDirection = direction.DIRECTION_ONE;
        }
    }





    // The following methods are for inputs incoming to this subclass

    /**
     * 
     */
    public void pedestrianInput(direction requestedCrossingDirection) { 
        switch (requestedCrossingDirection) {

            case DIRECTION_ONE:
                if (currentDirection != direction.DIRECTION_ONE) {
                    this.intersectionTimer.start();
                }
                break;
            
            case DIRECTION_TWO:
                if (currentDirection == direction.DIRECTION_ONE) { 
                    this.intersectionTimer.start();
                }
                break;
            
            default:
                break;
        } 

    }

    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) { 

    }






    public void shortenDirectionDuration(int timeToShortenBy) { 
        this.intersectionTimer.shortenCountDownTimer(timeToShortenBy);
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
