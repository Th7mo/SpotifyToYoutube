package dao;

import builder.HttpURLConnectionDirector;
import builder.SpotifyPlaylistConnectionBuilder;
import model.SpotifyPlaylist;
import exceptionhandler.SpotifyPlaylistDAOExceptionHandler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

public class SpotifyPlaylistDAO implements ISpotifyPlaylistDAO {

    private HttpURLConnection connection;
    private String accessToken;
    private String playlistId;
    private int offset = 0;

    @Override
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
        String responseJson = getResponse();

        return getBuildPlaylist(responseJson);
    }

    private void initializeConnection() throws IOException {
        SpotifyPlaylistConnectionBuilder builder = new SpotifyPlaylistConnectionBuilder();
        builder.setPlaylistURL(playlistId, offset);
        HttpURLConnectionDirector director = new HttpURLConnectionDirector(builder);
        director.makeSpotifyPlaylistConnection(accessToken);
        connection = builder.getResult();
    }

    private String getResponse() throws IOException {
        BufferedReader reader = getBufferedReader();

        return buildResponseString(reader);
    }

    private BufferedReader getBufferedReader() throws IOException {
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        return new BufferedReader(inputStreamReader);
    }

    private String buildResponseString(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining());
    }

    private SpotifyPlaylist getBuildPlaylist(String json) {
        return new Gson().fromJson(json, SpotifyPlaylist.class);
    }
}
