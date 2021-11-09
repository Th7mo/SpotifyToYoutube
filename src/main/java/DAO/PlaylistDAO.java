package DAO;

import Model.Playlist;

import java.io.IOException;

public interface PlaylistDAO {

	Playlist getPlaylist() throws IOException;
}
