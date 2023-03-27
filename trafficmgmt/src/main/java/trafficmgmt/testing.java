package trafficmgmt;

import trafficmgmt.utility.direction;

public class testing {
    public static void main(String[] args) throws InterruptedException { 
        //twowayIntersecion twowayTest = new twowayIntersecion("glue street", 10);
        //twowayPedestrianTest2(twowayTest);

        //threewayIntersection threewayTest = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        //threewayPedestrianTest1(threewayTest);

        fourwayIntersection fourwayTest = new fourwayIntersection(40, 40, 5, 0, "glue street", "paper street");
        fourwayCarTest1(fourwayTest);
    }

    // Pedestrian causes traffic light to count down then the other direction's
    // crosswalks are able to go
    public static void twowayPedestrianTest1(twowayIntersecion twowayTest) throws InterruptedException { 
        twowayTest.startIntersection();
        // Pedestrian test
        twowayTest.pedestrianInput(direction.DIRECTION_TWO);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(twowayTest.curerntDirectionTiming);
            twowayTest.printAllStates();
        } 
    }

    // Pedestrian wants to cross in the same direction as the cars 
    // so nothing happens since that is the usual way traffic goes
    public static void twowayPedestrianTest2(twowayIntersecion twowayTest) throws InterruptedException { 
        twowayTest.startIntersection();
        // Pedestrian test
        twowayTest.pedestrianInput(direction.DIRECTION_ONE);
        while (true) { 
            Thread.sleep(1000);
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

    public static void threewayPedestrianTest1(threewayIntersection threewayTest) throws InterruptedException { 
        threewayTest.startIntersection();
        // Pedestrian test
        threewayTest.pedestrianInput(direction.DIRECTION_TWO);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(threewayTest.curerntDirectionTiming);
            threewayTest.printAllStates();
        } 
    }

    // Pedestrian wants to cross in the same direction as the cars 
    // so nothing happens since that is the usual way traffic goes
    public static void threewayPedestrianTest2(threewayIntersection threewayTest) throws InterruptedException { 
        threewayTest.startIntersection();
        // Pedestrian test
        //threewayTest.pedestrianInput(direction.DIRECTION_ONE);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(threewayTest.curerntDirectionTiming);
            threewayTest.printAllStates();
        } 
    }

    // Traffic light counts down to let train pass then waits for train to 
    // signal it's gone. The thread.sleep needs to be longer than the countdown
    // to close the intersection
    public static void threewayCarTest(threewayIntersection threewayTest) throws InterruptedException { 
        threewayTest.carWeightInput(direction.DIRECTION_TWO, direction.DIRECTION_ONE, 50);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(threewayTest.curerntDirectionTiming);
            threewayTest.printAllStates();
        } 
    }

    
    public static void fourwayBasicTest1(fourwayIntersection fourwayTest) throws InterruptedException { 
        fourwayTest.startIntersection();
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(fourwayTest.curerntDirectionTiming);
            fourwayTest.printAllStates();
        } 
    }

    public static void fourwayPedestrianTest1(fourwayIntersection fourwayTest) throws InterruptedException { 
        fourwayTest.startIntersection();
        fourwayTest.pedestrianInput(direction.DIRECTION_ONE);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(fourwayTest.curerntDirectionTiming);
            fourwayTest.printAllStates();
        } 
    }


    public static void fourwayCarTest1(fourwayIntersection fourwayTest) throws InterruptedException { 
        fourwayTest.startIntersection();
        fourwayTest.carWeightInput(direction.DIRECTION_ONE, direction.DIRECTION_TWO, 50);
        while (true) { 
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");  
            System.out.println(fourwayTest.curerntDirectionTiming);
            fourwayTest.printAllStates();
        } 
    }
}
