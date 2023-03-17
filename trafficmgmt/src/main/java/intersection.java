package main.java;

abstract class Intersection {
    public abstract int startIntersection();
    public abstract int stopIntersection();
    
    public abstract int inputOptimization();
    public abstract String viewOptimization();
    public abstract int applyOptimization();

    public abstract void changeTrafficLightTiming(String direction, int newLength);
    public abstract void changeLeftTurnTiming(String dirction, int newLength);
    public abstract void changeCrossWalkTiming(direction, newLength);
    public abstract int getTrafficLightTiming(direction);
    public abstract int getLeftTurnTiming(direction);
    public abstract int getCrossWalkTiming(direction);
    
    public abstract void shortenCurrentTrafficLightDuration(timeToShortenBy);
    public abstract void shortenCurrentCrossWalkDuration(timeToShortenBy);
    public abstract void pedestrianInput(requestedCrossingDirection);
    public abstract void carWeightInput(startDirection, crossingDirection, weight);
}