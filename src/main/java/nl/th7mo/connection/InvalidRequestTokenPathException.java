package nl.th7mo.connection;

public class InvalidRequestTokenPathException extends BadRequestException {

    public InvalidRequestTokenPathException(String errorMessage) {
        super(errorMessage);
    }
}
