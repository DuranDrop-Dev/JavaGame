package src.assets.util;

public class NoNullException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Input cannot be empty";
    }
}
