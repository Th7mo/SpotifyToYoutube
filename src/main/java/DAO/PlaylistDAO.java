package DAO;

import Model.Playlist;

import java.io.IOException;

public interface PlaylistDAO {

	Playlist getPlaylist(String accessToken) throws IOException;
}
