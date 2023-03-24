package trafficmgmt;

/**
 * This class is soley focused on the creating a countdown timer
 * and nothing else. The minimum possible logic for that functionallity 
 * is implemented here. Any other logic needed should be implemented in
 * the calling class.
 */
public class timer extends Thread {
    Intersection intersection;
    int timeToCountDownFrom;
    int currentTimeInCountDown;
    boolean leftTurn; 
    int leftTurnGreenLength;

    // Constructor
    public timer(Intersection intersection) { 
        this.intersection = intersection;
        this.timeToCountDownFrom = intersection.getTimeToCountDownFrom();
        //this.leftTurn = intersection.
    }

    /* 
    public timer(int timeToCountDownFrom, systemAdminstrator sysAdmin) { 
        this.timeToCountDownFrom = timeToCountDownFrom;
        // make an instance variable to store the system admin 
        // to send the messages from the catch below to
    }
    */

    // Function to shorten the currently running timer. Used by run().
    public void shortenCountDownTimer(int timeToShortenBy) { 
        this.timeToCountDownFrom = this.timeToCountDownFrom - timeToShortenBy;
    }

    // Function to return to caller the status of the timer.
    public int getcurrentTimeInCountDown() { 
        return this.currentTimeInCountDown;
    }

    // Function that sleeps for the current Thread for the amount of the timer
    public void run() { 
        int intialTimeToCountDownFrom = this.timeToCountDownFrom;
        for (int i = this.timeToCountDownFrom; i >= 0; i--) {
            currentTimeInCountDown = i;
            try { 
                /* !IMPORTANT
                 * This if statment assumes that the calling class has implemented safeguards
                 * to not allow things like the timer going below 0, being shortened below
                 * the minimum light length, etc..
                 */
                if (this.timeToCountDownFrom < intialTimeToCountDownFrom) { 
                    i = i - (intialTimeToCountDownFrom - this.timeToCountDownFrom);
                }
                
                Thread.sleep(1000);

            } catch (InterruptedException e) { 
                // Send error to system admin error log
            }
        }
    }

}
