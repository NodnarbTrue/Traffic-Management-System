package trafficmgmt;
import java.util.ArrayList;

public class twowayIntersecion extends Intersection{
    protected ArrayList<crosswalk> directionTwoCrosswalks;

    public twowayIntersecion(int directionOneLightLength, String intersectionRoadOneName) {
        super(directionOneLightLength, intersectionRoadOneName);

        trafficlight firstDirectionTrafficlightOne = new  trafficlight(direction.DIRECTION_ONE, null);
        trafficlight firstDirectionTrafficlightTwo = new  trafficlight(direction.DIRECTION_ONE, null);
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

    public int startIntersection() { 
        this.currentGreenLightDirection = direction.DIRECTION_ONE;
        
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



    public void changeTrafficLightTiming(direction direction, int newLength) { 
 
    }

    public void changeLeftTurnTiming(direction dirction, int newLength) { 

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






    public int inputOptimization() { 

        return 1;
    }

    public String viewOptimization() { 

        return "success";
    }

    public int applyOptimization() {

        return 1;
    }
}
