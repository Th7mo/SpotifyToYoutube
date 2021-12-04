package nl.th7mo.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.collect.Lists;

import io.github.cdimascio.dotenv.Dotenv;

import nl.th7mo.spotify.playlist.Item;
import nl.th7mo.spotify.playlist.SpotifyPlaylist;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

public class YoutubePlaylistDAO {

    private YouTube youtube;
    private String playlistId;

    private final SpotifyPlaylist spotifyPlaylist;
    private final String youtubeApiKey;
    private final YoutubePlaylistItemDAO youtubePlaylistItemDAO = new YoutubePlaylistItemDAO();

    public YoutubePlaylistDAO (SpotifyPlaylist spotifyPlaylist) {
        this.spotifyPlaylist = spotifyPlaylist;
        Dotenv dotenvVariableLoader = Dotenv.load();
        this.youtubeApiKey = dotenvVariableLoader.get("YOUTUBE_API_KEY");
        init();
    }

    private void init() {
        try {
            setYoutubeObject();
            youtubePlaylistItemDAO.setYoutube(youtube);
            this.playlistId = insertEmptyPlaylist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setYoutubeObject() throws IOException {
        List<String> requestScopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        Credential credential = Authorisation.authorize(requestScopes, "playlistupdates");
        youtube = YoutubeBuilder.buildYoutube(credential);
    }

    private String insertEmptyPlaylist() throws IOException {
        Playlist youTubePlaylist = YoutubeBuilder.getYoutubePlaylistObject(spotifyPlaylist);
        List<String> part = Collections.singletonList("snippet,status");
        YouTube.Playlists.Insert playlistInsertCommand = youtube
                .playlists()
                .insert(part, youTubePlaylist);
        Playlist playlistInserted = playlistInsertCommand.execute();

        return playlistInserted.getId();
    }

    public void postPlaylist() {
        youtube = YoutubeBuilder.buildYoutube();
        List<Item> items = spotifyPlaylist.getItems();
        YoutubePlaylistIdDAO playlistIdDAO = new YoutubePlaylistIdDAO(youtubeApiKey, youtube);

        for (Item item : items) {
            try {
                SearchResult result = playlistIdDAO.getSearchResult(item);
                addToYoutube(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addToYoutube(SearchResult searchResult) throws IOException {
        String youtubeVideoId = searchResult.getId().getVideoId();
        youtubePlaylistItemDAO.insertPlaylistItem(playlistId, youtubeVideoId);
    }
}
