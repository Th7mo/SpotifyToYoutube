package DAO;

import Model.Playlist;
import Model.SpotifyPlaylist;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SpotifyPlaylistDAO implements PlaylistDAO {

	private HttpURLConnection connection;
	private String accessToken;
	private final String PLAYLIST_URL = "https://api.spotify.com/v1/playlists/4MWtutmJdwJLX8p1SsTQal/tracks";

	public SpotifyPlaylistDAO() {}

	@Override
	public Playlist getPlaylist(String accessToken) throws IOException {
		this.accessToken = accessToken;
		initializeConnection();
		String responseJson = getResponse();

		return getBuildPlaylist(responseJson);
	}

	private void initializeConnection() throws IOException {
		URL url = new URL(PLAYLIST_URL);
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

	private Playlist getBuildPlaylist(String json) {
		return new Gson().fromJson(json, SpotifyPlaylist.class);
	}
}
