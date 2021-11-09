package Exception;

public class InvalidPlaylistIdException extends BadRequestException {

	public InvalidPlaylistIdException(String errorMessage) {
		super(errorMessage);
	}
}
