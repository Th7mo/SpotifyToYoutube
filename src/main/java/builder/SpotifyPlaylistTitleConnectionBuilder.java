package builder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyPlaylistTitleConnectionBuilder implements HttpURLConnectionBuilder {

	private HttpURLConnection connection;
	private String playlistURL;

	public void setPlaylistUrl(String playlistId) {
		String playlistBaseURL = "https://api.spotify.com/v1/playlists/";
		String filter = "?fields=name";
		playlistURL = playlistBaseURL + playlistId + filter;
	}

	@Override
	public void openConnection() throws IOException {
		URL url = new URL(playlistURL);
		connection = (HttpURLConnection) url.openConnection();
	}

	@Override
	public void setRequestMethod(String requestMethod) throws IOException {
		connection.setRequestMethod(requestMethod);
	}

	@Override
	public void setOutput(boolean isOutputConnection) {
		connection.setDoOutput(isOutputConnection);
	}

	@Override
	public void setRequestProperty(String key, String value) {
		connection.setRequestProperty(key, value);
	}

	public HttpURLConnection getResult() {
		return connection;
	}
}
