package trafficmgmt;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;

public class twowayIntersecion extends Intersection{
    protected ArrayList<crosswalk> directionTwoCrosswalks;
    // The length of a countdown for when the two way intersection gets a train or padestrian input
    int inputCountDownLength; 


    // Constructor
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
        directionOneCrosswalks.add(secondDirectionCrosswalkOne);
        directionOneCrosswalks.add(secondDirectionCrosswalkTwo);
    }

    /**
     * starting the intersection by setting the current direction lights and crosswalk
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

        // countdown would be here but two way intersections only countdown
        // when they get a signal from a padestrian wanting to cross the
        // opposite direction or if a trian signal is recived

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


    public void shortenCurrentTrafficLightDuration(int timeToShortenBy) { 

    }

    public void shortenCurrentCrossWalkDuration(int timeToShortenBy) { 

    }

    public int getTimeToCountDownFrom() { 
        return this.inputCountDownLength;
    }

    private void countDown() { 
        timer inputCountDown = new timer(this);
        inputCountDown.start();
    }

    public void pedestrianInput(direction requestedCrossingDirection) { 
        switch (requestedCrossingDirection) {
            case DIRECTION_ONE:
                if (currentDirection != direction.DIRECTION_ONE) {
                    countDown();
                }
                break;
            case DIRECTION_TWO:
                if (currentDirection == direction.DIRECTION_ONE) { 

                }
                break;
            default:
                break;
        } 

    }

    public void carWeightInput(direction startDirection, direction crossingDirection, int weight) { 

    }





    /* BEING REMOVED START */
    public void changeTrafficLightTiming(direction direction, int newLength) { 
        for (trafficlight i : directionOneTrafficLights) {
            i.setLightLength(lightState.GREEN, newLength);
        }
    }

    public void changeLeftTurnTiming(direction dirction, int newLength) { 
        for (trafficlight i : directionOneTrafficLights) {
            i.setLightLength(lightState.GREEN, newLength);
        }
    }
    /* BEING REMOVED END */

    
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
