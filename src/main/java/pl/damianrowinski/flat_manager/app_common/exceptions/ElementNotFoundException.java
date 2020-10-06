package pl.damianrowinski.flat_manager.app_common.exceptions;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }
}
