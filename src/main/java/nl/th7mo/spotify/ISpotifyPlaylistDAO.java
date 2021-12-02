package nl.th7mo.spotify;

import java.io.IOException;

public interface ISpotifyPlaylistDAO {

    SpotifyPlaylist getPlaylist(String accessToken, String playlistId) throws IOException;
}
