package trafficmgmt;

import trafficmgmt.utility.intersectionType;

import trafficmgmt.exceptions.NonIntegerException;
import trafficmgmt.exceptions.OverwriteException;

public class sysadmin {

    // Variables
    protected int ID;
    intersectionType type;
    Intersection curIntersection;
    Intersection nexIntersection;

    // Constructor
    public sysadmin(int ID) {
        this.curIntersection = new threewayIntersection(15, 10, 10, "North/south", "East/west");
        this.type = intersectionType.THREE_WAY;
        this.ID = ID;
    }

    public Intersection getIntersection() {
        return curIntersection;
    }

    public void inputData(String roadName[], utility.intersectionType intersectionTyp, String data[][])
            throws NonIntegerException, IllegalArgumentException, OverwriteException {

        int rowSize = 2;
        int columnSize = 3;

        if (intersectionTyp == intersectionType.FOUR_WAY) {
            rowSize = 4;
        } else if (intersectionTyp == intersectionType.THREE_WAY) {
            rowSize = 3;
        }

        if (data.length < rowSize || data[0].length < columnSize) {
            throw new IllegalArgumentException("Cannot pass an empty matrix");
        }

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

        type = intersectionTyp;
        if (intersectionTyp == intersectionType.TWO_WAY) {
            twowayIntersecion intersection = new twowayIntersecion(roadOne, 30);
            intersection.inputOptimization(convertedData);
            nexIntersection = intersection;
        } else if (intersectionTyp == intersectionType.THREE_WAY) {
            threewayIntersection intersection = new threewayIntersection(30, 30, 5, roadOne, roadTwo);
            intersection.inputOptimization(convertedData);
            nexIntersection = intersection;
        } else {
            fourwayIntersection intersection = new fourwayIntersection(30, 30, 5, 5, roadOne, roadTwo);
            intersection.inputOptimization(convertedData);
            nexIntersection = intersection;
        }
    }

    public int applyOptimization() {
        curIntersection = nexIntersection;
        if (type == intersectionType.TWO_WAY) {
            return ((twowayIntersecion) curIntersection).applyOptimization();
        } else if (type == intersectionType.THREE_WAY) {
            return ((threewayIntersection) curIntersection).applyOptimization();
        } else if (type == intersectionType.FOUR_WAY) {
            return ((fourwayIntersection) curIntersection).applyOptimization();
        } else {
            return -1;
        }
    }
}
