package nl.th7mo.youtube;

import nl.th7mo.spotify.playlist.SpotifyPlaylist;

public class YoutubeController {

    public void postPlaylist(SpotifyPlaylist spotifyPlaylist, String apiKey) {
        YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO(spotifyPlaylist, apiKey);
        playlistDAO.postPlaylist();
    }
}
