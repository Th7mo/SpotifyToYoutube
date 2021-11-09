package DAO;

import Model.AuthenticationOptions;
import Model.SpotifyToken;
import Exception.*;
import Util.SpotifyTokenDAOExceptionHandler;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SpotifyTokenDAO implements TokenDAO {

	private HttpURLConnection connection;

	public SpotifyTokenDAO() {}

	@Override
	public SpotifyToken getToken() throws IOException, BadRequestException {
		initializeConnection();
		sendRequest();
		SpotifyTokenDAOExceptionHandler.handleStatusCodes(connection);
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

	private void sendRequest() throws IOException {
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(getRequestData());
	}

	private byte[] getRequestData() {
		String request = "grant_type=client_credentials&client_id=" +
				AuthenticationOptions.CLIENT_ID +
				"&client_secret=" +
				AuthenticationOptions.CLIENT_SECRET;

		return request.getBytes(StandardCharsets.UTF_8);
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
		return reader.lines().collect(Collectors.joining("/n"));
	}

	private SpotifyToken getBuildSpotifyToken(String json) {
		return new Gson().fromJson(json, SpotifyToken.class);
	}
}
