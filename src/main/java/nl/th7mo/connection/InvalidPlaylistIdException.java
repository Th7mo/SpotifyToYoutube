package nl.th7mo.connection;

public class InvalidPlaylistIdException extends BadRequestException {

    public InvalidPlaylistIdException(String errorMessage) {
        super(errorMessage);
    }
}
