package Exception;

public class InvalidAccessTokenException extends BadRequestException {

	public InvalidAccessTokenException(String errorMessage) {
		super(errorMessage);
	}
}
