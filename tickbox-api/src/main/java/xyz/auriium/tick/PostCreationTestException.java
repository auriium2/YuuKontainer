package xyz.auriium.tick;

public class PostCreationTestException extends TickException {

    public PostCreationTestException(Throwable cause) {
        super("An exception was thrown even though your docker provider reported valid! Please check your provider. Exception: " + cause);
    }
}
