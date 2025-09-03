package lux.util;

/**
 * An Exception that is thrown when command is not recognised.
 */
public class NoCommandException extends Exception {
    public NoCommandException(String errorMessage) {
        super(errorMessage);
    }
}
