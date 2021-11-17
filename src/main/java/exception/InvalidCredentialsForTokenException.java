package exception;

public class InvalidCredentialsForTokenException extends BadRequestException {

    public InvalidCredentialsForTokenException(String errorMessage) {
        super(errorMessage);
    }
}
