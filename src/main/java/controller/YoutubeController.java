package controller;

import com.google.gson.Gson;
import dao.YoutubePlaylistDAO;
import model.YoutubeOath;
import model.SpotifyPlaylist;
import model.YoutubeCredentials;
import model.YoutubePlaylist;
import view.YoutubeCredentialsView;

import java.io.*;

public class YoutubeController {

    private final YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO();
    private YoutubeCredentials credentials;

    public void saveYoutubeCredentials() throws IOException {
        String pathToCredentials = "src/main/resources/youtube_credentials.json";
        credentials = new YoutubeCredentials();
        credentials.setApiKey(YoutubeCredentialsView.getApiKey());
        YoutubeOath oath = new YoutubeOath();
        oath.setClient_id(YoutubeCredentialsView.getClientId());
        oath.setClient_secret(YoutubeCredentialsView.getClientSecret());
        credentials.setInstalled(oath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToCredentials));
        String json = new Gson().toJson(credentials);
        writer.write(json);
        writer.close();
    }

    public void postPlaylist(SpotifyPlaylist spotifyPlaylist) {
        playlistDAO.setCredentials(credentials);
        YoutubePlaylist youtubePlaylist = playlistDAO.getPlaylist(spotifyPlaylist);
        playlistDAO.postPlaylist(youtubePlaylist, spotifyPlaylist);
    }
}
