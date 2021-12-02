package controller;

import model.SpotifyPlaylist;

import java.io.IOException;

public class ApplicationController {

    private final SpotifyController spotifyController = new SpotifyController();
    private final YoutubeController youtubeController = new YoutubeController();

    public void convert(String playlistId) throws IOException {
        spotifyController.setSpotifyPlaylist(playlistId);
        SpotifyPlaylist spotifyPlaylist = spotifyController.getSpotifyPlaylist();
        youtubeController.postPlaylist(spotifyPlaylist);
    }
}
