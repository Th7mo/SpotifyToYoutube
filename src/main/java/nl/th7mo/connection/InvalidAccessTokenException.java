package nl.th7mo.connection;

public class InvalidAccessTokenException extends BadRequestException {

    public InvalidAccessTokenException(String errorMessage) {
        super(errorMessage);
    }
}
