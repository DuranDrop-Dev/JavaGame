package src.assets.util;
public class InputLengthLimitException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Input length should not exceed 10 characters";
    }
}
