package main;

import dao.*;
import model.SpotifyPlaylist;
import model.SpotifyToken;
import model.YoutubePlaylist;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String playlistId = "0rBbrxmYoRfekosc63tbss";
        SpotifyTokenDAO spotifyTokenDAO = new SpotifyTokenDAO();
        SpotifyToken spotifyToken = spotifyTokenDAO.getToken();
        String tokenString = spotifyToken.getAccess_token();
        SpotifyPlaylistDAO spotifyPlaylistDAO = new SpotifyPlaylistDAO();
        SpotifyPlaylist spotifyPlaylist = spotifyPlaylistDAO.getPlaylist(tokenString, playlistId);
        SpotifyPlaylistTitleDAO titleDAO = new SpotifyPlaylistTitleDAO();
        SpotifyPlaylist playlistWithTitle = titleDAO.getPlaylistWithTitle(spotifyPlaylist, tokenString, playlistId);
        YoutubePlaylistDAO youtubePlaylistDAO = new YoutubePlaylistDAO();
        YoutubePlaylist youtubePlaylist = youtubePlaylistDAO.getPlaylist(playlistWithTitle);
        youtubePlaylistDAO.postPlaylist(youtubePlaylist, playlistWithTitle);
    }
}
