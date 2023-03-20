package trafficmgmt;
import java.util.ArrayList;

public class twowayIntersecion extends Intersection{
    protected ArrayList<String> directionTwoCrosswalks;

    public twowayIntersecion(int directionOneLightLength, String intersectionRoadOneName) {
        super(directionOneLightLength, intersectionRoadOneName);

        
    }

    public int startIntersection() { 

        return 1;
    };

    public int stopIntersection() { 

        return 1;
    }

    public int inputOptimization() { 

        return 1;
    }

    public String viewOptimization() { 

        return "sucess";
    }

    public int applyOptimization() {

        return 1;
    }

    public void changeTrafficLightTiming(String direction, int newLength) { 

    }

    public void changeLeftTurnTiming(String dirction, int newLength) { 

    }

    public void changeCrossWalkTiming(String direction, int newLength) { 

    }

    public int getTrafficLightTiming(String direction) { 
        
        return 1;
    }

    public int getLeftTurnTiming(String direction) { 

        return 1;
    }

    public int getCrossWalkTiming(String direction) { 

        return 1;
    }

    public void shortenCurrentTrafficLightDuration(int timeToShortenBy) { 

    }

    public void shortenCurrentCrossWalkDuration(int timeToShortenBy) { 

    }

    public void pedestrianInput(String requestedCrossingDirection) { 

    }

    public void carWeightInput(String startDirection, String crossingDirection, int weight) { 

    }

}
