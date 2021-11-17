package exception;

public class InvalidRequestTokenPathException extends BadRequestException {

    public InvalidRequestTokenPathException(String errorMessage) {
        super(errorMessage);
    }
}
