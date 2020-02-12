package epam.by.Auction.exception;

public class UtilityException extends RuntimeException {
    public UtilityException() {
    }

    public UtilityException(String message) {
        super(message);
    }

    public UtilityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityException(Throwable cause) {
        super(cause);
    }
}
