package main;

import dao.*;
import model.SpotifyPlaylist;
import model.SpotifyToken;
import model.YoutubePlaylist;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
//		YoutubeTokenDAO youtubeTokenDAO = new YoutubeTokenDAO();
//		youtubeTokenDAO.getToken();

		SpotifyTokenDAO spotifyTokenDAO = new SpotifyTokenDAO();
		SpotifyToken spotifyToken = spotifyTokenDAO.getToken();
		SpotifyPlaylistDAO spotifyPlaylistDAO = new SpotifyPlaylistDAO();
		SpotifyPlaylist spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(spotifyToken.getAccess_token(), "2CLV0KGCl0UwTvipE4Ibss");
		YoutubePlaylistDAO youtubePlaylistDAO = new YoutubePlaylistDAO();
		YoutubePlaylist youtubePlaylist = youtubePlaylistDAO.getPlaylist(spotifyPlaylist);
		youtubePlaylistDAO.postPlaylist(youtubePlaylist, spotifyPlaylist);
	}
}
