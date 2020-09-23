package pl.damianrowinski.flat_manager.exceptions;

public class FrobiddenAccessException extends RuntimeException {
    public FrobiddenAccessException(String message) {
        super(message);
    }
}
