package trafficmgmt;

import trafficmgmt.utility.intersectionType;

import java.io.FilePermission;
import java.util.HashMap;
import trafficmgmt.exceptions.NonIntegerException;
import trafficmgmt.exceptions.OverwriteException;

public class sysadmin {

    // Variables
    protected int ID;
    HashMap<String, Intersection> intersectionsManaged = new HashMap<String, Intersection>();

    // Constructor
    public sysadmin(int ID) {
        this.ID = ID;
    }

    public Intersection getHashedIntersection(String strKey) {
        return this.intersectionsManaged.get(strKey);
    }

    public void addNewIntersection(String idNameOfIntersection, String newIntersectionToMake) {
        if (newIntersectionToMake == "Two-Way") {

            twowayIntersecion newIntersection = new twowayIntersecion("default", 30, 50);
            this.intersectionsManaged.put(idNameOfIntersection, newIntersection);
            newIntersection.startIntersection();

        } else if (newIntersectionToMake == "Three-Way") {

            threewayIntersection newIntersection = new threewayIntersection(25, 25, 10, "default", "default");
            this.intersectionsManaged.put(idNameOfIntersection, newIntersection);
            newIntersection.startIntersection();

        } else if (newIntersectionToMake == "Four-Way") {

            fourwayIntersection newIntersection = new fourwayIntersection(40, 40, 5, 0, "default", "default");
            this.intersectionsManaged.put(idNameOfIntersection, newIntersection);
            newIntersection.startIntersection();

        }

        System.out.println(this.intersectionsManaged);
    }

    public String inputData(Intersection intersection, String roadName[], utility.intersectionType intersectionTyp,
            String data[][])
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

        // @TODO: Find a way to set the roads.
        String roadOne = (roadName.length >= 1) ? roadName[0] : "DirectionOne";
        String roadTwo = (roadName.length >= 2) ? roadName[1] : "DirectionTwo";

        intersection.setRoadOneName(roadOne);
        intersection.setRoadTwoName(roadTwo);
        intersection.inputOptimization(convertedData);
        return intersection.viewOptimizationRecommendation();
    }

    public int applyOptimization(Intersection intersection) {
        if (intersection.intersectionClassification == intersectionType.TWO_WAY) {
            return ((twowayIntersecion) intersection).applyOptimization();
        } else if (intersection.intersectionClassification == intersectionType.THREE_WAY) {
            return ((threewayIntersection) intersection).applyOptimization();
        } else if (intersection.intersectionClassification == intersectionType.FOUR_WAY) {
            return ((fourwayIntersection) intersection).applyOptimization();
        } else {
            return -1;
        }
    }
}
