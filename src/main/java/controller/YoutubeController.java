package controller;

import com.google.gson.Gson;
import dao.YoutubePlaylistDAO;
import model.Installed;
import model.SpotifyPlaylist;
import model.YoutubeCredentials;
import model.YoutubePlaylist;
import view.YoutubeCredentialsView;

import java.io.*;

public class YoutubeController {

    private final YoutubePlaylistDAO playlistDAO = new YoutubePlaylistDAO();
    private YoutubeCredentials credentials;

    public void saveYoutubeCredentials() throws IOException {
        credentials = new YoutubeCredentials();
        credentials.setApiKey(YoutubeCredentialsView.getApiKey());
        Installed installed = new Installed();
        installed.setClient_id(YoutubeCredentialsView.getClientId());
        installed.setClient_secret(YoutubeCredentialsView.getClientSecret());
        credentials.setInstalled(installed);
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/youtube_credentials.json"));
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
