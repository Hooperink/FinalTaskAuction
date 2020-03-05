package epam.by.Auction.exception;

public class MessageErrorException extends Exception {
    public MessageErrorException() {
    }

    public MessageErrorException(String message) {
        super(message);
    }

    public MessageErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageErrorException(Throwable cause) {
        super(cause);
    }
}
