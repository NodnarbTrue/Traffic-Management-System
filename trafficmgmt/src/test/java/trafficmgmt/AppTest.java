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
}
