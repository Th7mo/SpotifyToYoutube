package nl.th7mo.connection;

public class InvalidCredentialsForTokenException extends BadRequestException {

    public InvalidCredentialsForTokenException(String errorMessage) {
        super(errorMessage);
    }
}
