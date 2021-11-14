package dao;

import model.SpotifyPlaylist;
import exceptionhandler.SpotifyPlaylistDAOExceptionHandler;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SpotifyPlaylistDAO implements ISpotifyPlaylistDAO {

	private final String playlistBaseURL = "https://api.spotify.com/v1/playlists/";

	private HttpURLConnection connection;
	private String accessToken;
	private int offset = 0;
	private String playlistURL;
	private String playlistId;

	@Override
	public SpotifyPlaylist getPlaylist(String accessToken, String playlistId)
			throws IOException {
		this.accessToken = accessToken;
		this.playlistId = playlistId;
		playlistURL = playlistBaseURL + playlistId + "/tracks?offset=";

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
		URL url = new URL(playlistURL + offset);
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization",
				"Bearer " + accessToken);
		connection.setRequestProperty("Content-Type", "application/json");
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
