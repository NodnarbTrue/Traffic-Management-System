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
    public void twowayChangeToRed() {
        lightState expected = lightState.RED;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 10);
        //Needs to be included because current direction is not initialed to direction one
        twowayTest.currentDirection = direction.DIRECTION_ONE;
        twowayTest.setAllCurrentDirectionTrafficLights(lightState.RED);
        lightState result = twowayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void threewayChangeToRed() {
        lightState expected = lightState.RED;
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        threewayTest.setAllCurrentDirectionTrafficLights(lightState.RED);
        lightState result = threewayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwayChangeToRed() {
        lightState expected = lightState.RED;
        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayTest.setAllCurrentDirectionTrafficLights(lightState.RED);
        lightState result = fourwayTest.directionOneTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void twowayChangeToStop() {
        crosswalkState expected = crosswalkState.STOP;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 10);
        //Needs to be included because current direction is not initialed to direction one
        twowayTest.currentDirection = direction.DIRECTION_ONE;
        twowayTest.setAllCurrentDirectionCrosswalk(crosswalkState.STOP);
        crosswalkState result = twowayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    @Test
    public void threewayChangeToStop() {
        crosswalkState expected = crosswalkState.STOP;
        threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        threewayTest.setAllCurrentDirectionCrosswalk(crosswalkState.STOP);
        crosswalkState result = threewayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwayChangeToCountdown() {
        crosswalkState expected = crosswalkState.COUNTDOWN;
        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayTest.setAllCurrentDirectionCrosswalk(crosswalkState.COUNTDOWN);
        crosswalkState result = fourwayTest.directionOneCrosswalks.get(0).getCurrentCrossWalkState();
        assertTrue(expected == result);
    }

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

    //Use Case 1: Pedestrian requests to cross intersection

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

    //Use Case 2: Train approaching intersection
    @Test
    public void twowayTrainApproaching() throws InterruptedException {
        int expected = 14;
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 15);
        twowayTest.trainIncomingSignal();
        Thread.sleep(1005); 
        int result = twowayTest.curerntDirectionTiming;
        assertTrue(expected == result);
    }

    //Use Case 3: Car approaches intersection from opposite direction
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

    //Use Case 4: Left turn light state

    @Test
    public void testThreewayLeftTurnState() throws InterruptedException {
        //Timer shouldn't shorten because it is less than the length of countdown timer
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

    //Use Case 5: Switch direction
    @Test
    public void threewaySwitchDirection() throws InterruptedException {
        //Timer shouldn't shorten because it is less than the length of countdown timer
        lightState expected = lightState.GREEN; 
        threewayIntersection threewayTest = new threewayIntersection(1, 25, 0, "glue street", "paper street");
        threewayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = threewayTest.directionTwoTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    @Test
    public void fourwaySwitchDirection() throws InterruptedException {
        fourwayIntersection fourwayTest = new fourwayIntersection(1, 25, 0, 0, "glue street", "paper street");
        lightState expected = lightState.GREEN; 
        fourwayTest.startIntersection();
        Thread.sleep(2005); 
        lightState result = fourwayTest.directionTwoTrafficLights.get(0).getCurrentLightState();
        assertTrue(expected == result);
    }

    //Use Case 6(8): Heavy traffic in opposite direction (heavy traffic will be considered 5 to lower testing time)

    @Test
    public void threewayTraffic() throws InterruptedException {
        //Should be equal to the countdown length because inputs can't shorten more than this
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
        //Should be equal to the countdown length because inputs can't shorten more than this
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
}
