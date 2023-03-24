package trafficmgmt;

import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;

public class threewayIntersection extends Intersection {
    protected ArrayList<crosswalk> directionTwoCrosswalks;
    private trafficlight directionTwoLight;

    public threewayIntersection(int directionOneLightLength, int directionTwoLightLength, int leftTurnLength,
            String intersectionRoadOneName) {
        super(directionOneLightLength, intersectionRoadOneName);
        // Assumes firstDirectionTrafficlight is the road that can turn left
        trafficlight firstDirectionTrafficlightOne = new trafficlight(direction.DIRECTION_ONE, directionOneLightLength,
                leftTurnLength);
        trafficlight firstDirectionTrafficlightTwo = new trafficlight(direction.DIRECTION_TWO, directionOneLightLength);

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

    public void changeTrafficLightTiming(direction dir, lightState light, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            for (trafficlight i : directionOneTrafficLights) {
                if (light == lightState.LEFT_TURN) {
                    // do something
                }

                i.setLightLength(light, newLength);
            }
        } else
            directionTwoLight.setLightLength(light, newLength);
    }

    public void changeLeftTurnTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_ONE) {
            directionTwoLight.setLightLength(lightState.LEFT_TURN, newLength);
        } else
            directionTwoLight.setLightLength(lightState.LEFT_TURN, newLength);
    }

    public void changeCrossWalkTiming(direction dir, int newLength) {
        if (dir == direction.DIRECTION_TWO) {
            for (crosswalk i : directionTwoCrosswalks) {
                i.setCrossWalkTiming(newLength);
            }
        } else
            directionOneCrosswalks.get(0).setCrossWalkTiming(newLength);
    }

    public int getTrafficLightTiming(direction dir) {

        if (dir == direction.DIRECTION_ONE)
            return directionOneTrafficLights.get(0).getLightTiming(lightState.GREEN);
        else
            return directionTwoLight.getLightTiming(lightState.GREEN);
    }

    public int getLeftTurnTiming(direction dir) {

        if (dir == direction.DIRECTION_ONE)
            return directionOneTrafficLights.get(0).getLightTiming(lightState.LEFT_TURN);
        else
            return directionTwoLight.getLightTiming(lightState.LEFT_TURN);
    }

    public int getCrossWalkTiming(direction dir) {

        if (dir == direction.DIRECTION_ONE)
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

    public int inputOptimization(Integer[][] input) {

        return 1;
    }

    public String viewOptimizationRecommendation() {

        return "success";
    }

    public int applyOptimization() {

        return 1;
    }

    public void changeTrafficLightTiming(direction dir, int time) {

    };
}
