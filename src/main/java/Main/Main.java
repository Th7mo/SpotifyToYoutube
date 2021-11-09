package Main;

import DAO.SpotifyTokenDAO;
import DAO.TokenDAO;
import Model.Token;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Logger.class.getName());
		logger.log(Level.INFO, "Trying to get Spotify Access Token...");
		TokenDAO spotifyTokenDAO = new SpotifyTokenDAO();

		try {
			Token token = spotifyTokenDAO.getToken();
			logger.log(Level.INFO, "Attempt successful. \n" +
					"Access token: " + token.getAccess_token());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
