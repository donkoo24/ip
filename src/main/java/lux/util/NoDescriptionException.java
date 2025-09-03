package lux.util;

/**
 * An Exception thrown when description for a command is missing.
 */
public class NoDescriptionException extends Exception {
    public NoDescriptionException(String errorMessage) {
        super(errorMessage);
    }
}
