package nl.th7mo.connection;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
