package DAO;

import Model.SpotifyPlaylist;

import java.io.IOException;

public interface ISpotifyPlaylistDAO {

	SpotifyPlaylist getPlaylist(String accessToken, String playlistId) throws IOException;
}
