package builder;

import java.io.IOException;

public class HttpURLConnectionDirector {

	HttpURLConnectionBuilder builder;

	public HttpURLConnectionDirector(HttpURLConnectionBuilder httpURLConnectionBuilder) {
		this.builder = httpURLConnectionBuilder;
	}

	public void makeSpotifyTokenConnection() throws IOException {
		builder.openConnection();
		builder.setRequestMethod("POST");
		builder.setOutput(true);
		builder.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
	}

	public void makeSpotifyPlaylistConnection(String accessToken)
			throws IOException {
		builder.openConnection();
		builder.setRequestMethod("GET");
		builder.setOutput(true);
		builder.setRequestProperty("Authorization", "Bearer " + accessToken);
		builder.setRequestProperty("Content-Type", "application/json");
	}
}
