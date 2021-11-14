package DAO;

import Model.Playlist;

import java.io.IOException;

public interface PlaylistDAO {

	Playlist getPlaylist(String accessToken, String playlistId) throws IOException;
}
