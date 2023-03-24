package trafficmgmt;
import java.util.ArrayList;

public class threewayIntersection extends Intersection{
    protected ArrayList<crosswalk> directionTwoCrosswalks;
    private trafficlight directionTwoLight;

    public threewayIntersection(ArrayList<direction> directions, int directionOneLightLength, int directionTwoLightLength, String intersectionRoadOneName) {
        super(directionOneLightLength, intersectionRoadOneName);
        //Plan is to add the lights based on what directions are in the list
        trafficlight firstDirectionTrafficlightOne, firstDirectionTrafficlightTwo = null;
        if (directions.contains(direction.NORTH) && directions.contains(direction.SOUTH)){
            firstDirectionTrafficlightOne = new  trafficlight(direction.NORTH, directionOneLightLength);
            firstDirectionTrafficlightTwo = new  trafficlight(direction.SOUTH, directionOneLightLength);
            if (directions.contains(direction.WEST))
                directionTwoLight = new trafficlight(direction.WEST, directionTwoLightLength);
            else
            directionTwoLight = new trafficlight(direction.EAST, directionTwoLightLength);
        }
        else{
            firstDirectionTrafficlightOne = new  trafficlight(direction.WEST, directionOneLightLength);
            firstDirectionTrafficlightTwo = new  trafficlight(direction.EAST, directionOneLightLength);
            if (directions.contains(direction.NORTH))
                directionTwoLight = new trafficlight(direction.NORTH, directionTwoLightLength);
            else
            directionTwoLight = new trafficlight(direction.SOUTH, directionTwoLightLength);
        }

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
            for (trafficlight i : directionOneTrafficLights) {
            i.setLightLength("left turn", newLength);
            }
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
