package trafficmgmt.exceptions;

public class NonIntegerException extends IllegalArgumentException {
    int i, j;

    public NonIntegerException(int i, int j, String val) {
        super(String.format("Expected integer in range [0, 999], got %s", val));
        this.i = i;
        this.j = j;
    }
}
