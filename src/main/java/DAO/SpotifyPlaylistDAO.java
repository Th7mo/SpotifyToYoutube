package DAO;

import Model.Playlist;
import Model.SpotifyPlaylist;

import java.io.IOException;

public class SpotifyPlaylistDAO implements PlaylistDAO {

	public SpotifyPlaylistDAO() {}

	@Override
	public Playlist getPlaylist() throws IOException {
		return new SpotifyPlaylist();
	}
}
