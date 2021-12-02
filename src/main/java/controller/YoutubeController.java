package controller;

import dao.YoutubePlaylistDAO;
import model.SpotifyPlaylist;
import model.YoutubePlaylist;

public class YoutubeController {

    private final YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO();

    public void postPlaylist(SpotifyPlaylist spotifyPlaylist) {
        YoutubePlaylist youtubePlaylist = playlistDAO.getPlaylist(spotifyPlaylist);
        playlistDAO.postPlaylist(youtubePlaylist, spotifyPlaylist);
    }
}
