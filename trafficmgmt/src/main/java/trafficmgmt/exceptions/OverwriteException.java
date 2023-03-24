package trafficmgmt.exceptions;

public class OverwriteException extends IllegalStateException {
    public OverwriteException(String message) {
        super(message);
    }
}
