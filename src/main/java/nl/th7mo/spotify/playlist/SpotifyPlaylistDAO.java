package nl.th7mo.spotify.playlist;

import com.google.gson.Gson;
import nl.th7mo.connection.HttpURLConnectionDirector;
import nl.th7mo.connection.ResponseBuilder;
import nl.th7mo.connection.SpotifyPlaylistConnectionBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SpotifyPlaylistDAO {

    private HttpURLConnection connection;
    private String accessToken;
    private String playlistId;
    private int offset = 0;

    public SpotifyPlaylist getPlaylist(String accessToken, String playlistId)
            throws IOException {
        this.accessToken = accessToken;
        this.playlistId = playlistId;

        return getCompletePlaylist();
    }

    private SpotifyPlaylist getCompletePlaylist() throws IOException {
        SpotifyPlaylist completePlaylist = new SpotifyPlaylist();
        SpotifyPlaylist partPlaylist = getPartPlaylist();

        while (!partPlaylist.isEmpty()) {
            completePlaylist.join(partPlaylist);
            increaseTrackOffset();
            partPlaylist = getPartPlaylist();
        }

        return completePlaylist;
    }

    private void increaseTrackOffset() {
        offset += 100;
    }

    private SpotifyPlaylist getPartPlaylist() throws IOException {
        initializeConnection();
        SpotifyPlaylistDAOExceptionHandler.handleStatusCodes(
                connection, accessToken, playlistId);
        String responseJson = ResponseBuilder.getResponse(connection);

        return getBuildPlaylist(responseJson);
    }

    private void initializeConnection() throws IOException {
        SpotifyPlaylistConnectionBuilder builder = new SpotifyPlaylistConnectionBuilder();
        builder.setPlaylistURL(playlistId, offset);
        HttpURLConnectionDirector director = new HttpURLConnectionDirector(builder);
        director.makeSpotifyPlaylistConnection(accessToken);
        connection = builder.getResult();
    }

    private SpotifyPlaylist getBuildPlaylist(String json) {
        return new Gson().fromJson(json, SpotifyPlaylist.class);
    }
}
