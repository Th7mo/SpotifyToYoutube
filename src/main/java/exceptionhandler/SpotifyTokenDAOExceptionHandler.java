package exceptionhandler;

import enumerator.StatusCode;
import model.SpotifyAuthorizationOptions;
import exception.*;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SpotifyTokenDAOExceptionHandler {

    private static HttpURLConnection connection;

    public static void handleStatusCodes(HttpURLConnection connection)
            throws IOException, BadRequestException {
        SpotifyTokenDAOExceptionHandler.connection = connection;
        int responseCode = getResponseCode();

        if (responseCode != StatusCode.OK.codeNumber()) {
            handleBadRequestStatusCodes(responseCode);
        }
    }

    private static int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    private static void handleBadRequestStatusCodes(int responseCode)
            throws InvalidCredentialsForTokenException,
            InvalidRequestTokenPathException {
        if (responseCode == StatusCode.BAD_REQUEST.codeNumber()) {
            throwInvalidCredentialsForTokenException();
        }

        throwInvalidRequestTokenPathException();
    }

    private static void throwInvalidCredentialsForTokenException()
            throws InvalidCredentialsForTokenException {
        throw new InvalidCredentialsForTokenException(
                "Bad Request, Credentials for token are invalid:" +
                "\n\nClient_Id: " +
                SpotifyAuthorizationOptions.CLIENT_ID +
                "\nClient_Secret: " +
                SpotifyAuthorizationOptions.CLIENT_SECRET);
    }

    private static void throwInvalidRequestTokenPathException()
            throws InvalidRequestTokenPathException {
        throw new InvalidRequestTokenPathException(
                "Bad Request, path to the token endpoint is wrong: " +
                "\nPath: " + SpotifyAuthorizationOptions.TOKEN_URL);
    }
}
