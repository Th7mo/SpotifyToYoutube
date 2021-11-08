package Main;

import DAO.SpotifyTokenDAO;
import Model.SpotifyToken;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Logger.class.getName());
		logger.log(Level.INFO, "Trying to get Spotify Access Token...");
		SpotifyTokenDAO spotifyTokenDAO = new SpotifyTokenDAO();

		try {
			SpotifyToken token = spotifyTokenDAO.getToken();
			logger.log(Level.INFO, "Attempt successful. \n" +
					"Access token: " + token.getAccess_token());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
