package nl.th7mo.youtube;

import nl.th7mo.spotify.playlist.SpotifyPlaylist;

public class YoutubeController {

    public void postPlaylist(SpotifyPlaylist spotifyPlaylist) {
        YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO(spotifyPlaylist);
        playlistDAO.postPlaylist();
    }
}
