package trafficmgmt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

     @Test
    public void threewaySignalsCountdown() throws InterruptedException {
        crosswalkState expected = crosswalkState.COUNTDOWN; 
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        //2 second wait because crosswalk is started as walk even if light length is shorter than countdown
        Thread.sleep(2005); 
        crosswalkState result = threewayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    //USE CASE 1: PEDESTRIAN REQUESTS TO CROSS INTERSECTION

    @Test
    public void twowayPedestrianCrossRequest() throws InterruptedException {
        int expected = 14;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 15);
        twowayTest.startIntersection();
        twowayTest.pedestrianInput(direction.DIRECTION_TWO);
        //Waits to process change due to input
        Thread.sleep(1005); 
        int result = twowayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 > countdown
    @Test
    public void threewayPedestrianCrossRequest() throws InterruptedException {
        int expected = 54;
        threewayIntersection threewayTest = new threewayIntersection(60, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        threewayTest.pedestrianInput(direction.DIRECTION_TWO);
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 < countdown
    @Test
    public void threewayPedestrianCrossRequest2() throws InterruptedException {
        //Timer shouldn't shorten because it is less than the length of countdown timer
        int expected = 33;
        threewayIntersection threewayTest = new threewayIntersection(34, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        threewayTest.pedestrianInput(direction.DIRECTION_TWO);
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 > countdown
    @Test
    public void fourwayPedestrianCrossRequest() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(60, 25, 0, 0, "glue street", "paper street");
        int expected = 54; 
        fourwayTest.startIntersection();
        fourwayTest.pedestrianInput(direction.DIRECTION_TWO);
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 < countdown
    @Test
    public void fourwayPedestrianCrossRequest2() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(34, 25, 0, 0, "glue street", "paper street");
        int expected = 33; 
        fourwayTest.startIntersection();
        fourwayTest.pedestrianInput(direction.DIRECTION_TWO);
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //USE CASE 2: TRAIN APPROACHING INTERSECTION
    @Test
    public void twowayTrainApproaching() throws InterruptedException {
        int expected = 14;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 15);
        twowayTest.trainIncomingSignal();
        Thread.sleep(1005); 
        int result = twowayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //USE CASE 3: CAR APPROACHES INTERSECTION FROM OPPOSITE DIRECTION
    //timer - 5 > countdown
    @Test
    public void twowayCarCrossRequest() throws InterruptedException {
        int expected = 44;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 15);
        twowayTest.startIntersection();
        //Car input only has an effect if pedestrian is crossing
        twowayTest.switchDirection();
        twowayTest.carWeightInput(direction.DIRECTION_ONE, direction.DIRECTION_TWO, 200);
        Thread.sleep(1005); 
        int result = twowayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    @Test
    public void threewayCarCrossRequest() throws InterruptedException {
        int expected = 54;
        threewayIntersection threewayTest = new threewayIntersection(60, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        threewayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200); 
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 < countdown
    @Test
    public void threewayCarCrossRequest2() throws InterruptedException {
        //Timer shouldn't shorten because it is less than the length of countdown timer
        int expected = 33;
        threewayIntersection threewayTest = new threewayIntersection(34, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        threewayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 > countdown
    @Test
    public void fourwayCarCrossRequest() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(60, 25, 0, 0, "glue street", "paper street");
        int expected = 54; 
        fourwayTest.startIntersection();
        fourwayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //timer - 5 < countdown
    @Test
    public void fourwayCarCrossRequest2() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(34, 25, 0, 0, "glue street", "paper street");
        int expected = 33; 
        fourwayTest.startIntersection();
        fourwayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //USE CASE 4: LEFT TURN LIGHT STATE

    @Test
    public void testThreewayLeftTurnState() throws InterruptedException {
        lightState expected = lightState.LEFT_TURN; 
        threewayIntersection threewayTest = new threewayIntersection(34, 25, 10, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(1005); 
        lightState result = threewayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void testFourwayLeftTurnState() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(60, 25, 10, 10, "glue street", "paper street");
        lightState expected = lightState.LEFT_TURN; 
        fourwayTest.startIntersection();
        Thread.sleep(1005); 
        lightState result = fourwayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void testFourwayLeftTurnState2() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(60, 25, 10, 10, "glue street", "paper street");
        lightState expected = lightState.LEFT_TURN; 
        fourwayTest.startIntersection();
        fourwayTest.switchDirection();
        Thread.sleep(1005); 
        lightState result = fourwayTest.directionTwoTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    //USE CASE 5: SWITCH DIRECTION
    @Test
    public void threewaySwitchDirectionTurnsGreen() throws InterruptedException {
        lightState expected = lightState.GREEN; 
        threewayIntersection threewayTest = new threewayIntersection(1, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = threewayTest.directionTwoTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void threewaySwitchDirectionTurnsRed() throws InterruptedException {
        lightState expected = lightState.RED; 
        threewayIntersection threewayTest = new threewayIntersection(1, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = threewayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void threewaySwitchDirectionSignalsWalk() throws InterruptedException {
        crosswalkState expected = crosswalkState.WALK; 
        threewayIntersection threewayTest = new threewayIntersection(1, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(2005); 
        crosswalkState result = threewayTest.directionTwoCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    @Test
    public void threewaySwitchDirectionSignalsStop() throws InterruptedException {
        crosswalkState expected = crosswalkState.STOP; 
        threewayIntersection threewayTest = new threewayIntersection(1, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(2005); 
        crosswalkState result = threewayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwaySwitchDirectionTurnsGreen() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(1, 25, 0, 0, "glue street", "paper street");
        lightState expected = lightState.GREEN; 
        fourwayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = fourwayTest.directionTwoTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwaySwitchDirectionTurnsRed() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(1, 25, 0, 0, "glue street", "paper street");
        lightState expected = lightState.RED; 
        fourwayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = fourwayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwaySwitchDirectionSignalsWalk() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(1, 25, 0, 0, "glue street", "paper street");
        crosswalkState expected = crosswalkState.WALK; 
        fourwayTest.startIntersection();
        Thread.sleep(2005); 
        crosswalkState result = fourwayTest.directionTwoCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwaySwitchDirectionSignalsStop() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(1, 25, 0, 0, "glue street", "paper street");
        crosswalkState expected = crosswalkState.STOP; 
        fourwayTest.startIntersection();
        Thread.sleep(2005); 
        crosswalkState result = fourwayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    //USE CASE 6(8): HEAVY TRAFFIC IN OPPOSITE DIRECTION (HEAVY TRAFFIC WILL BE CONSIDERED 5 TO LOWER TESTING TIME)

    @Test
    public void threewayTraffic() throws InterruptedException {
        //Should be equal to the countdown length - 1 because inputs can't shorten more than this
        int expected = 29;
        threewayIntersection threewayTest = new threewayIntersection(50, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        for (int i = 0; i < 5; i++){
            threewayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
            Thread.sleep(1005); 
        }
        //Needs this sleep to process the final input, don't know why it isn't done in the loop?
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    @Test
    public void threewayTraffic2() throws InterruptedException {
        // 100 - 4 inputs (20s) - 5 waits (5s) - final input (5s) - 1s wait
        int expected = 69;
        threewayIntersection threewayTest = new threewayIntersection(100, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        for (int i = 0; i < 5; i++){
            threewayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
            Thread.sleep(1005); 
        }
        Thread.sleep(1005); 
        int result = threewayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    @Test
    public void fourwayTraffic() throws InterruptedException {
        //Should be equal to the countdown length - 1 because inputs can't shorten more than this
        int expected = 29;
        fourwayIntersection fourwayTest = new fourwayIntersection(60, 25, 0, 0, "glue street", "paper street");
        fourwayTest.startIntersection();
        for (int i = 0; i < 5; i++){
            fourwayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
            Thread.sleep(1005); 
        }
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    @Test
    public void fourwayTraffic2() throws InterruptedException {
        // 100 - 4 inputs (20s) - 5 waits (5s) - final input (5s) - 1s wait
        int expected = 69;
        fourwayIntersection fourwayTest = new fourwayIntersection(100, 25, 0, 0, "glue street", "paper street");
        fourwayTest.startIntersection();
        for (int i = 0; i < 5; i++){
            fourwayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 200);
            Thread.sleep(1005); 
        }
        Thread.sleep(1005); 
        int result = fourwayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //USE CASE 7 (11): SYSTEM ADMIN DIRECTLY CHANGES INTERSECTION TIMINGS
    @Test
    public void twowayChangeCrossWalkTiming() {
        int expected = 40;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 10);
        twowayTest.changeCrossWalkTiming(direction.DIRECTION_TWO, 40);
        int result = twowayTest.getCrossWalkTiming(direction.DIRECTION_TWO);
        assertTrue(expected == result);
    }

    @Test
    public void threewayChangeCrossWalkTiming() {
        int expected = 40;
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        threewayTest.changeCrossWalkTiming(direction.DIRECTION_ONE, 40);
        int result = threewayTest.getCrossWalkTiming(direction.DIRECTION_ONE);
        assertTrue(expected == result);
    }

    @Test
    public void fourwayChangeCrossWalkTiming() {
        int expected = 40;
        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayTest.changeCrossWalkTiming(direction.DIRECTION_TWO, 40);
        int result = fourwayTest.getCrossWalkTiming(direction.DIRECTION_TWO);
        assertTrue(expected == result);
    }

    @Test
    public void threewayChangeLightTiming() {
        int expected = 60;
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        threewayTest.changeTrafficLightTiming(direction.DIRECTION_ONE, 60);
        int result = threewayTest.getTrafficLightTiming(direction.DIRECTION_ONE);
        assertTrue(expected == result);
    }

    @Test
    public void fourwayChangeLightTiming() {
        int expected = 60;
        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayTest.changeTrafficLightTiming(direction.DIRECTION_TWO, 60);
        int result = fourwayTest.getTrafficLightTiming(direction.DIRECTION_TWO);
        assertTrue(expected == result);
    }

    @Test
    public void threewayChangeLeftTurnTiming() {
        int expected = 20;
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        threewayTest.changeLeftTurnTiming(direction.DIRECTION_ONE, 20);
        int result = threewayTest.getLeftTurnTiming(direction.DIRECTION_ONE);
        assertTrue(expected == result);
    }

    @Test
    public void fourwayChangeLeftTurnTiming() {
        int expected = 20;
        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayTest.changeTrafficLightTiming(direction.DIRECTION_TWO, 20);
        int result = fourwayTest.getTrafficLightTiming(direction.DIRECTION_TWO);
        assertTrue(expected == result);
    }
}
