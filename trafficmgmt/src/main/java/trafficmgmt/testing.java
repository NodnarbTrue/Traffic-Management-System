package trafficmgmt;

import trafficmgmt.utility.direction;

public class testing {
    public static void main(String[] args) throws InterruptedException { 
        twowayIntersecion twowayTest = new twowayIntersecion("glue street", 10);
        twowayPedestrianTest2(twowayTest);


    }

    // Pedestrian causes traffic light to count down then the other direction's
    // crosswalks are able to go
    public static void twowayPedestrianTest1(twowayIntersecion twowayTest) { 
        twowayTest.startIntersection();
        // Pedestrian test
        twowayTest.pedestrianInput(direction.DIRECTION_TWO);
        while (true) { 
            System.out.print("\033[H\033[2J");  
            System.out.println(twowayTest.curerntDirectionTiming);
            twowayTest.printAllStates();
        } 
    }

    // Pedestrian wants to cross in the same direction as the cars 
    // so nothing happens since that is the usual way traffic goes
    public static void twowayPedestrianTest2(twowayIntersecion twowayTest) { 
        twowayTest.startIntersection();
        // Pedestrian test
        twowayTest.pedestrianInput(direction.DIRECTION_ONE);
        while (true) { 
            System.out.print("\033[H\033[2J");  
            System.out.println(twowayTest.curerntDirectionTiming);
            twowayTest.printAllStates();
        } 
    }

    // Traffic light counts down to let train pass then waits for train to 
    // signal it's gone. The thread.sleep needs to be longer than the countdown
    // to close the intersection
    public static void twowayTrainTest(twowayIntersecion twowayTest) throws InterruptedException { 
        System.out.print("\033[H\033[2J");  
        System.out.println(twowayTest.curerntDirectionTiming);
        twowayTest.printAllStates();

        twowayTest.trainIncomingSignal();

        System.out.print("\033[H\033[2J");  
        System.out.println(twowayTest.curerntDirectionTiming);
        twowayTest.printAllStates();

        Thread.sleep(15000);
        
        System.out.print("\033[H\033[2J");  
        System.out.println(twowayTest.curerntDirectionTiming);
        twowayTest.printAllStates();

        twowayTest.trainClearSignal();

        while (true) { 
            System.out.print("\033[H\033[2J");  
            System.out.println(twowayTest.curerntDirectionTiming);
            twowayTest.printAllStates();
        } 
    }

    

}
