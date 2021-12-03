package nl.th7mo.spotify.playlist;

import com.google.gson.Gson;
import nl.th7mo.connection.HttpURLConnectionDirector;
import nl.th7mo.connection.ResponseBuilder;
import nl.th7mo.connection.SpotifyPlaylistTitleConnectionBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SpotifyPlaylistTitleDAO {

	private SpotifyPlaylist playlist;
	private String accessToken;
	private String playlistId;
	private HttpURLConnection connection;

	public SpotifyPlaylist getPlaylistWithTitle(SpotifyPlaylist playlist,
												String accessToken,
												String playlistId) throws IOException {
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
		String response = ResponseBuilder.getResponse(connection);
		addTitle(response);
	}

	private void addTitle(String json) {
		SpotifyPlaylist playlistWithTitle =
				new Gson().fromJson(json, SpotifyPlaylist.class);
		playlist.setName(playlistWithTitle.getName());
	}
}
