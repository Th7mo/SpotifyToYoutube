package nl.th7mo.convert;

import nl.th7mo.spotify.SpotifyPlaylist;
import nl.th7mo.spotify.SpotifyController;
import nl.th7mo.youtube.YoutubeController;

import java.io.IOException;

public class ApplicationController {

    private final SpotifyController spotifyController = new SpotifyController();
    private final YoutubeController youtubeController = new YoutubeController();

    public void convert(String playlistId, String apiKey) throws IOException {
        spotifyController.setSpotifyPlaylist(playlistId);
        SpotifyPlaylist spotifyPlaylist = spotifyController.getSpotifyPlaylist();
        youtubeController.postPlaylist(spotifyPlaylist, apiKey);
    }
}
