package Main;

import DAO.PlaylistDAO;
import DAO.SpotifyPlaylistDAO;
import DAO.SpotifyTokenDAO;
import DAO.TokenDAO;
import Model.Item;
import Model.Playlist;
import Model.SpotifyPlaylist;
import Model.Token;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Logger.class.getName());
		logger.log(Level.INFO, "Trying to get Spotify Access Token...");
		TokenDAO spotifyTokenDAO = new SpotifyTokenDAO();
		PlaylistDAO playlistDAO = new SpotifyPlaylistDAO();

		try {
			Token token = spotifyTokenDAO.getToken();
			logger.log(Level.INFO, "Attempt successful. \n" +
					"Access token: " + token.getAccess_token());
			Playlist spotifyPlaylist = playlistDAO.getPlaylist(token.getAccess_token());

			for (Item item : spotifyPlaylist.getItems()) {
				System.out.println(item.getTrack().getName() + " : " + item.getTrack().getArtist(0).getName());
			}

			System.out.println(spotifyPlaylist.getItems().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
