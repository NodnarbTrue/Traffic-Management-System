package trafficmgmt;
import java.util.ArrayList;

public class threewayIntersection extends Intersection{
    protected ArrayList<crosswalk> directionTwoCrosswalks;
    private trafficlight directionTwoLight;

    public threewayIntersection(ArrayList<direction> directions, int directionOneLightLength, int directionTwoLightLength, int leftTurnLength, String intersectionRoadOneName) {
        super(directionOneLightLength, intersectionRoadOneName);
        //Assumes firstDirectionTrafficlight is the road that can turn left
        trafficLight firstDirectionTrafficlightOne = new  trafficlight(direction.DIRECTION_ONE, directionOneLightLength, leftTurnLength);
        trafficLight firstDirectionTrafficlightTwo = new  trafficlight(direction.DIRECTION_TWO, directionOneLightLength);
        
        directionTwoLight = new trafficlight(direction.DIRECTION_TWO, directionTwoLightLength);

        directionOneTrafficLights.add(firstDirectionTrafficlightOne);
        directionOneTrafficLights.add(firstDirectionTrafficlightTwo);

        crosswalk directionOneCrosswalk = new crosswalk(direction.DIRECTION_ONE);
        directionOneCrosswalks.add(directionOneCrosswalk);
        
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
        if (direction == trafficmgmt.Intersection.direction.DIRECTION_ONE){
            for (trafficlight i : directionOneTrafficLights) {
            i.setLightLength("green", newLength);
            }
        }
        else
            directionTwoLight.setLightLength("green", newLength);
    }

    public void changeLeftTurnTiming(direction direction, int newLength) { 
        if (direction == trafficmgmt.Intersection.direction.DIRECTION_ONE){
            directionTwoTrafficLights.get(0).setLightLength("left turn", newLength)
        }
        else
            directionTwoLight.setLightLength("left turn", newLength);
    }

    public void changeCrossWalkTiming(direction direction, int newLength) { 
        if (direction == trafficmgmt.Intersection.direction.DIRECTION_TWO){
            for (crosswalk i : directionTwoCrosswalks) {
            i.setCrossWalkTiming(newLength);
            }
        }
        else
            directionOneCrosswalks.get(0).setCrossWalkTiming(newLength);
    }

    public int getTrafficLightTiming(direction direction) { 
        
        if (direction == trafficmgmt.Intersection.direction.DIRECTION_ONE)
            return directionOneTrafficLights.get(0).getLightTiming("green");
        else
            return directionTwoLight.getLightTiming("green");
    }

    public int getLeftTurnTiming(direction direction) { 

        if (direction == trafficmgmt.Intersection.direction.DIRECTION_ONE)
            return directionOneTrafficLights.get(0).getLightTiming("left turn");
        else
            return directionTwoLight.getLightTiming("left turn");
    }

    public int getCrossWalkTiming(direction direction) { 

        if (direction == trafficmgmt.Intersection.direction.DIRECTION_ONE)
            return directionOneCrosswalks.get(0).getCrossWalkTiming();
        else
            return directionTwoCrosswalks.get(0).getCrossWalkTiming();
    }

    public void shortenCurrentTrafficLightDuration(int timeToShortenBy) { 
        if ((this.currentGreenLightTimer - timeToShortenBy) > minimumLightLength)
            this.currentGreenLightTimer -= timeToShortenBy;
    }

    public void shortenCurrentCrossWalkDuration(int timeToShortenBy) { 
        if ((this.currentCrosswalkLightTimer - timeToShortenBy) > minimumLightLength)
                this.currentCrosswalkLightTimer -= timeToShortenBy;
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
