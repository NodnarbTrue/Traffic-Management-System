package trafficmgmt;

import trafficmgmt.utility.intersectionType;
import trafficmgmt.exceptions.NonIntegerException;
import trafficmgmt.exceptions.OverwriteException;

public class sysadmin {

    // Variables
    protected int ID;
    intersectionType type;
    Intersection nexIntersection;

    // Constructor
    public sysadmin(int ID) {
        this.ID = ID;
    }

    public Intersection inputData(String roadName[], utility.intersectionType intersectionTyp, String data[][])
            throws NonIntegerException, IllegalArgumentException, OverwriteException {

        if (data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Cannot pass an empty matrix");
        }

        int rowSize = data.length;
        int columnSize = data[0].length;

        Integer[][] convertedData = new Integer[rowSize][columnSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                try {
                    int converted = Integer.parseInt(data[i][j]);
                    if (converted >= 0 && converted <= 999) {
                        convertedData[i][j] = converted;
                    }
                } catch (NumberFormatException e) {
                    throw new NonIntegerException(i, j, data[i][j]);
                }
            }

        }

        String roadOne = (roadName.length >= 1) ? roadName[0] : "DirectionOne";
        String roadTwo = (roadName.length >= 2) ? roadName[1] : "DirectionTwo";

        if (intersectionTyp == intersectionType.TWO_WAY) {
            type = intersectionTyp;
            twowayIntersecion intersection = new twowayIntersecion(roadOne, 30);
            intersection.inputOptimization(convertedData);
            return intersection;
        } else if (intersectionTyp == intersectionType.THREE_WAY) {
            threewayIntersection intersection = new threewayIntersection(30, 30, 5, roadOne, roadTwo);
            intersection.inputOptimization(convertedData);
            nexIntersection = intersection;
            return nexIntersection;
        } else {
            fourwayIntersection intersection = new fourwayIntersection(30, 30, 5, 5, roadOne, roadTwo);
            intersection.inputOptimization(convertedData);
            nexIntersection = intersection;
            return nexIntersection;
        }
    }

    public int applyOptimization() {
        if (type == intersectionType.TWO_WAY) {
            return ((twowayIntersecion) nexIntersection).applyOptimization();
        } else if (type == intersectionType.THREE_WAY) {
            return ((threewayIntersection) nexIntersection).applyOptimization();
        } else if (type == intersectionType.FOUR_WAY) {
            return ((fourwayIntersection) nexIntersection).applyOptimization();
        } else {
            return -1;
        }
    }
}
