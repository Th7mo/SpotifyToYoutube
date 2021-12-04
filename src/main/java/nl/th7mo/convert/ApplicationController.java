package nl.th7mo.convert;

import nl.th7mo.spotify.playlist.SpotifyPlaylist;
import nl.th7mo.spotify.SpotifyController;
import nl.th7mo.youtube.YoutubeController;

import java.io.IOException;

public class ApplicationController {

    public void convert(String playlistId) throws IOException {
        SpotifyController spotifyController = new SpotifyController();
        spotifyController.setSpotifyPlaylist(playlistId);
        SpotifyPlaylist spotifyPlaylist = spotifyController.getSpotifyPlaylist();
        new YoutubeController().postPlaylist(spotifyPlaylist);
    }
}
