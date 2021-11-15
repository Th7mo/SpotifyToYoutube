package dao;

import builder.HttpURLConnectionDirector;
import builder.SpotifyPlaylistTitleConnectionBuilder;
import com.google.gson.Gson;
import model.SpotifyPlaylist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

public class SpotifyPlaylistTitleDAO {

	private SpotifyPlaylist playlist;
	private String accessToken;
	private String playlistId;
	private HttpURLConnection connection;

	public SpotifyPlaylist getPlaylistWithTitle(SpotifyPlaylist playlist,
									   String accessToken, String playlistId)
			throws IOException {
		this.playlist = playlist;
		this.accessToken = accessToken;
		this.playlistId = playlistId;
		initializeConnection();
		addResponse();

		return playlist;
	}

	private void initializeConnection() throws IOException {
		SpotifyPlaylistTitleConnectionBuilder builder =
				new SpotifyPlaylistTitleConnectionBuilder();
		builder.setPlaylistUrl(playlistId);
		HttpURLConnectionDirector director = new HttpURLConnectionDirector(builder);
		director.makeSpotifyPlaylistTitleConnection(accessToken);
		connection = builder.getResult();
	}

	private void addResponse() throws IOException {
		BufferedReader reader = getBufferedReader();
		addTitle(buildResponseString(reader));
	}

	private BufferedReader getBufferedReader() throws IOException {
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		return new BufferedReader(inputStreamReader);
	}

	private String buildResponseString(BufferedReader reader) {
		return reader.lines().collect(Collectors.joining());
	}

	private void addTitle(String json) {
		SpotifyPlaylist playlistWithTitle =
				new Gson().fromJson(json, SpotifyPlaylist.class);
		playlist.setName(playlistWithTitle.getName());
	}
}
