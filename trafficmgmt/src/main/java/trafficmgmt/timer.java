package trafficmgmt;

import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.timerlengthinformation;

/**
 * This class is focused on the creating a countdown timer
 * and having many side effects to change the state of the 
 * system based on the current time of the timer.
 */
public class timer extends Thread {
    Intersection intersection;
    direction currentDirection;

    int timeToCountDownFrom;
    boolean leftTurnExsists; 
    int leftTurnGreenLength;
    int yellowLightLength;
    int crosswalkCoutdownLength;

    boolean shortenTimer;
    int proposedTimeToShortenBy;
    int currentTimeInCountDown;



    // Constructor
    public timer(Intersection intersection) { 
        this.intersection = intersection;
        this.shortenTimer = false;
        
        updateTimerInformation();
    }



    /**
     * Updates the information of the timer based on the intersection's
     * objects. This is helpful for resuing the same timer through the entire
     * lifetime of the intersection because it allows the same timer to be used
     * for two directions, and it also reflects new changes made by system admin 
     * in the next time the timer for a particular change runs. 
     */
    private void updateTimerInformation() { 
        /**
         * A timer needs three things
         * 1. The length of time to count down from. This is the total length of a light from green to red.
         * 2. The length of time for left turn arrow and a boolean for if a left turn arrow exsists.
         * 3. The length of time for yellow light.
         * 4. The length of time a crosswalk counter is.
         */

        this.timeToCountDownFrom = intersection.getTimeToCountDownFrom();
        this.leftTurnExsists = intersection.getCurrentDirectionLeftTurnExsistance();
        if (this.leftTurnExsists == true) { 
            this.leftTurnGreenLength = intersection.getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH);
        } else { 
            this.leftTurnGreenLength = 0;
        }
        this.yellowLightLength = intersection.getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH);
        this.crosswalkCoutdownLength = intersection.getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH);
    }



    /**
     * Method for changing the state of any component in the system.
     * It has side effects on the current direction's traffic lights 
     * and crosswalks in the instance intersection.
     */
    private void changeSystemStatesBasedOnTimer() { 
        // left turn at the start of green light
        if (this.timeToCountDownFrom == this.currentTimeInCountDown){
            if (this.leftTurnExsists == true) {
                this.intersection.setAllCurrentDirectionTrafficLights(lightState.LEFT_TURN);
                this.intersection.setAllCurrentDirectionCrosswalk(crosswalkState.STOP);
            } else {
                this.intersection.setAllCurrentDirectionTrafficLights(lightState.GREEN);
                this.intersection.setAllCurrentDirectionCrosswalk(crosswalkState.WALK);
            }
        }

        // left turn green period ends
        else if ((this.timeToCountDownFrom - this.leftTurnGreenLength) == this.currentTimeInCountDown) {
            if (this.leftTurnExsists == true) { 
                this.intersection.setAllCurrentDirectionTrafficLights(lightState.GREEN);
                this.intersection.setAllCurrentDirectionCrosswalk(crosswalkState.WALK);
            }
        }

        // crosswalk starts to count down
        else if (this.crosswalkCoutdownLength == this.currentTimeInCountDown) {
            this.intersection.setAllCurrentDirectionCrosswalk(crosswalkState.COUNTDOWN);
        }

        // yellow lights start
        else if (this.yellowLightLength == this.currentTimeInCountDown) { 
            this.intersection.setAllCurrentDirectionTrafficLights(lightState.YELLOW);
            if (this.currentTimeInCountDown < this.crosswalkCoutdownLength) { 
                this.intersection.decrementCurrentDirectionCrossWalkTimer();
            }
        }

        // between the crosswalk count down and end of light length
        else if ((this.currentTimeInCountDown < this.crosswalkCoutdownLength) &&
                (this.currentTimeInCountDown > 0)) { 
            this.intersection.decrementCurrentDirectionCrossWalkTimer();
        }

        // the direction is done it's countdown
        else if (this.currentTimeInCountDown == 0) { 
            this.intersection.decrementCurrentDirectionCrossWalkTimer();
            this.intersection.setAllCurrentDirectionTrafficLights(lightState.RED);
            this.intersection.setAllCurrentDirectionCrosswalk(crosswalkState.STOP);
        }

        else { 
            // SYS ADMIN ERROR LOG HERE
        }
    }



    /**
     * Method that sleeps for the length of a greenlight/direction
     * counts down the time and changes the states of the system
     * based on the current time left.s
     */
    public void run() { 
        for (int i = this.timeToCountDownFrom; i >= 0; i--) {
            this.currentTimeInCountDown = i;
            this.changeSystemStatesBasedOnTimer();

            try { 
                if (this.shortenTimer == true) { 
                    /* !Important
                     * only changes the current direction live length based on input if the 
                     * new proposed live length is greather than both the crosswalk countdown
                     * and the yellow light length
                     */
                    this.shortenTimer = false;
                    int tempi = i - this.proposedTimeToShortenBy;
                    if ((tempi >= this.crosswalkCoutdownLength) && (tempi > this.yellowLightLength)) { 
                        i = tempi;
                        this.currentTimeInCountDown = i;
                        this.changeSystemStatesBasedOnTimer();
                    } 
                    // DOES THIS NEED AN ELSE?
                }
                
                Thread.sleep(1000);

            } catch (InterruptedException e) { 
                // Send error to system admin error log
            }
        }

        this.intersection.switchDirection();
        this.updateTimerInformation();
    }



    // Function to shorten the currently running timer. Used by run().
    public void shortenCountDownTimer(int timeToShortenBy) { 
        this.shortenTimer = true;
        this.proposedTimeToShortenBy = timeToShortenBy;
    }

    // Function to return to caller the status of the timer.
    public int getcurrentTimeInCountDown() { 
        return this.currentTimeInCountDown;
    }

    /* 
    public timer(int timeToCountDownFrom, systemAdminstrator sysAdmin) { 
        this.timeToCountDownFrom = timeToCountDownFrom;
        // make an instance variable to store the system admin 
        // to send the messages from the catch below to
    }
    */
}
