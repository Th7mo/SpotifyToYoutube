package nl.th7mo.youtube;

import nl.th7mo.spotify.SpotifyPlaylist;

public class YoutubeController {

    private final YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO();

    public void postPlaylist(SpotifyPlaylist spotifyPlaylist) {
        YoutubePlaylist youtubePlaylist = playlistDAO.getPlaylist(spotifyPlaylist);
        playlistDAO.postPlaylist(youtubePlaylist, spotifyPlaylist);
    }
}
