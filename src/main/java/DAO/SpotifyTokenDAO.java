package DAO;

import Model.AuthenticationOptions;
import Model.SpotifyToken;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SpotifyTokenDAO {

	private HttpURLConnection connection;

	public SpotifyToken getToken() throws IOException {
		initializeConnection();
		sendRequest();
		String responseJson = getResponse();

		return getBuildSpotifyToken(responseJson);
	}

	private void initializeConnection() throws IOException {
		URL url = new URL(AuthenticationOptions.TOKEN_URL);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
	}

	private byte[] getRequestData() {
		String request = "grant_type=client_credentials&client_id=" +
				AuthenticationOptions.CLIENT_ID +
				"&client_secret=" +
				AuthenticationOptions.CLIENT_SECRET;

		return request.getBytes(StandardCharsets.UTF_8);
	}

	private void sendRequest() throws IOException {
		OutputStream stream = connection.getOutputStream();
		stream.write(getRequestData());
	}

	private String getResponse() throws IOException {
		BufferedReader reader = getBufferedReader();
		StringBuilder response = new StringBuilder();

		return buildResponseString(reader, response);
	}

	private BufferedReader getBufferedReader() throws IOException {
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		return new BufferedReader(inputStreamReader);
	}

	private String buildResponseString(BufferedReader reader,
									   StringBuilder response) throws IOException {
		String currentLine = reader.readLine();

		while (currentLine != null) {
			response.append(currentLine);
			response.append("\n");
			currentLine = reader.readLine();
		}

		return response.toString();
	}

	private SpotifyToken getBuildSpotifyToken(String json) {
		return new Gson().fromJson(json, SpotifyToken.class);
	}
}
