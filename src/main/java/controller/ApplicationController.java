package controller;

import model.SpotifyPlaylist;

import java.io.IOException;

public class ApplicationController {

    private final SpotifyController spotifyController = new SpotifyController();
    private final YoutubeController youtubeController = new YoutubeController();

    public void convert() throws IOException {
        spotifyController.setSpotifyPlaylist();
        SpotifyPlaylist spotifyPlaylist = spotifyController.getSpotifyPlaylist();
        youtubeController.saveYoutubeCredentials();
        youtubeController.postPlaylist(spotifyPlaylist);
    }
}
