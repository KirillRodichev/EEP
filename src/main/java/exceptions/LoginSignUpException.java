package exceptions;

public class LoginSignUpException extends Exception {
    public LoginSignUpException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
