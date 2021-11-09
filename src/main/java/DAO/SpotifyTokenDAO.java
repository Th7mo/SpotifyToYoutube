package DAO;

import Model.AuthenticationOptions;
import Model.STATUS_CODE;
import Model.SpotifyToken;
import Exception.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SpotifyTokenDAO implements TokenDAO {

	private HttpURLConnection connection;

	@Override
	public SpotifyToken getToken() throws IOException, BadRequestException {
		initializeConnection();
		sendRequest();
		int responseCode = getResponseCode();

		if (responseCode == STATUS_CODE.OK.codeNumber()) {
			String responseJson = getResponse();

			return getBuildSpotifyToken(responseJson);
		}

		if (responseCode == STATUS_CODE.BAD_REQUEST.codeNumber()) {
			throw new InvalidCredentialsForTokenException(
					"Bad Request, Credentials for token are invalid:" +
							"\n\nClient_Id: " +
							AuthenticationOptions.CLIENT_ID +
							"\nClient_Secret: " +
							AuthenticationOptions.CLIENT_SECRET);
		}

		throw new InvalidRequestTokenPathException(
				"Bad Request, path to the token endpoint is wrong: " +
						"\nPath: " + AuthenticationOptions.TOKEN_URL);
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

	private int getResponseCode() throws IOException {
		return connection.getResponseCode();
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
