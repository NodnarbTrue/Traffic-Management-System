package trafficmgmt;

import java.security.Identity;
import java.util.ArrayList;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.timerlengthinformation;
import trafficmgmt.exceptions.OverwriteException;

public class sysadmin {

    // Variables
    protected int ID;

    // Constructor
    public sysadmin(int ID) {
        this.ID = Identity;
    }

    // Methods

    public Intersection inputData(String roadName, utility.intersectionType intersectionType, String data[][]) {

        Throws NonIntegerException, IllegalArgumentException, OverwriteException;
        {
            if (data.length == 0 || data[0].length == 0) {
                throw new IllegalArguemntException("Cannot pass an empty matrix");
            }
        }

        int rowSize = data.length;
        int columnSize = data[0].length;

        Integer[][] convertedData = new Integer[rowSize][columnSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (data[i][j] == Integer(data[i][j]) && (Integer(data[i][j]) >= 0 && Integer(data[i][j]) <= 999)) {
                    convertedData[i][j] = Integer(data[i][j]);
                } else {
                    throw new NonIntegerException(i, j);
                }
            }

        }
        if (intersectionType == TWO_WAY) {
            TwoWayIntersection intersection = new TwoWayIntersection(roadName, 30);
            intersection.inputOptimization(convertedData);
            return this.nextIntersection;
        } else if (intersectionType == THREE_WAY) {
            ThreeWayIntersection intersection = new ThreeWayIntersection(roadName, 30);
            intersection.inputOptimization(convertedData);
            return this.nextIntersection;
        } else {
            FourWayIntersection intersection = new FourWayIntersection(roadName, 30);
            intersection.inputOptimization(convertedData);
            return this.nextIntersection;
        }
    }

    public int applyOptimization() {
        intersection.applyOptimization();
    }
}
