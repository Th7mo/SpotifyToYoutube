package nl.th7mo.spotify;

import nl.th7mo.connection.StatusCode;
import nl.th7mo.connection.BadRequestException;
import nl.th7mo.connection.InvalidAccessTokenException;
import nl.th7mo.connection.InvalidCredentialsForTokenException;
import nl.th7mo.connection.InvalidPlaylistIdException;
import nl.th7mo.connection.InvalidRequestTokenPathException;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SpotifyPlaylistDAOExceptionHandler {

    private static HttpURLConnection connection;
    private static String accessToken;
    private static String playlistId;

    public static void handleStatusCodes(HttpURLConnection connection,
                                         String accessToken, String playlistId)
            throws IOException, BadRequestException {
        SpotifyPlaylistDAOExceptionHandler.connection = connection;
        SpotifyPlaylistDAOExceptionHandler.accessToken = accessToken;
        SpotifyPlaylistDAOExceptionHandler.playlistId = playlistId;
        int responseCode = getResponseCode();

        if (responseCode != StatusCode.OK.codeNumber()) {
            handleBadRequestStatusCodes(responseCode);
        }
    }

    private static int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    private static void handleBadRequestStatusCodes(int responseCode)
            throws InvalidCredentialsForTokenException, InvalidRequestTokenPathException {
        if (responseCode == StatusCode.UNAUTHORIZED.codeNumber()) {
            throwInvalidAccessTokenException();
        }

        throwInvalidPlaylistIdException();
    }

    private static void throwInvalidAccessTokenException()
            throws InvalidAccessTokenException {
        throw new InvalidAccessTokenException(
                "Bad Request, The Spotify API does not" +
                "recognize this Access Token:" +
                "\nAccessToken: " + accessToken);
    }

    private static void throwInvalidPlaylistIdException()
            throws InvalidPlaylistIdException {
        throw new InvalidPlaylistIdException(
                "Playlist could not be found, The Spotify API does not: " +
                "recognize this playlistId" +
                "\nPlaylistId: " + playlistId);
    }
}
