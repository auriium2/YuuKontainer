package xyz.auriium.kontainer;

public class PostCreationTestException extends KontainerException {

    public PostCreationTestException(Throwable cause) {
        super("An exception was thrown even though your docker provider reported valid! Please check your provider. Exception: " + cause);
    }
}
