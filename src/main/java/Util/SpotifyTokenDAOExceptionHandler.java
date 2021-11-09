package Util;

import Model.AuthenticationOptions;
import Enum.STATUS_CODE;
import Exception.*;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SpotifyTokenDAOExceptionHandler {

	private static HttpURLConnection connection;

	public static void handleStatusCodes(HttpURLConnection connection)
			throws IOException, BadRequestException {
		SpotifyTokenDAOExceptionHandler.connection = connection;
		int responseCode = getResponseCode();

		if (responseCode != STATUS_CODE.OK.codeNumber()) {
			handleBadRequestStatusCodes(responseCode);
		}
	}

	private static int getResponseCode() throws IOException {
		return connection.getResponseCode();
	}

	private static void handleBadRequestStatusCodes(int responseCode)
			throws InvalidCredentialsForTokenException,
			InvalidRequestTokenPathException {
		if (responseCode == STATUS_CODE.BAD_REQUEST.codeNumber()) {
			throwInvalidCredentialsForTokenException();
		}

		throwInvalidRequestTokenPathException();
	}

	private static void throwInvalidCredentialsForTokenException()
			throws InvalidCredentialsForTokenException {
		throw new InvalidCredentialsForTokenException(
				"Bad Request, Credentials for token are invalid:" +
						"\n\nClient_Id: " +
						AuthenticationOptions.CLIENT_ID +
						"\nClient_Secret: " +
						AuthenticationOptions.CLIENT_SECRET);
	}

	private static void throwInvalidRequestTokenPathException()
			throws InvalidRequestTokenPathException {
		throw new InvalidRequestTokenPathException(
				"Bad Request, path to the token endpoint is wrong: " +
						"\nPath: " + AuthenticationOptions.TOKEN_URL);
	}
}
