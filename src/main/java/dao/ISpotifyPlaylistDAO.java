package dao;

import model.SpotifyPlaylist;

import java.io.IOException;

public interface ISpotifyPlaylistDAO {

    SpotifyPlaylist getPlaylist(String accessToken, String playlistId) throws IOException;
}
