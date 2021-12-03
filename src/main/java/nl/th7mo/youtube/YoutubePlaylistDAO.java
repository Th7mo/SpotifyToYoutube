package nl.th7mo.youtube;

import nl.th7mo.spotify.Item;
import nl.th7mo.spotify.SpotifyPlaylist;

import nl.th7mo.util.Authorisation;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.*;
import java.util.*;

public class YoutubePlaylistDAO {

    private YouTube youtube;
    private final SpotifyPlaylist spotifyPlaylist;
    private final String key;
    private String playlistId;
    private final YoutubePlaylistItemDAO youtubePlaylistItemDAO = new YoutubePlaylistItemDAO();

    public YoutubePlaylistDAO (SpotifyPlaylist spotifyPlaylist, String key) {
        this.spotifyPlaylist = spotifyPlaylist;
        this.key = key;
        init();
    }

    private void init() {
        try {
            setYoutubeObject();
            youtubePlaylistItemDAO.setYoutube(youtube);
            this.playlistId = insertEmptyPlaylist();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setYoutubeObject() throws IOException {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        Credential credential = Authorisation.authorize(scopes, "playlistupdates");
        youtube = new YouTube.Builder(
                Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, credential)
                .setApplicationName("SpotifyToYoutube").build();
    }

    private String insertEmptyPlaylist() throws IOException {
        Playlist youTubePlaylist = getYoutubePlaylistObject();
        List<String> part = Collections.singletonList("snippet,status");
        YouTube.Playlists.Insert playlistInsertCommand =
                youtube.playlists().insert(part, youTubePlaylist);
        Playlist playlistInserted = playlistInsertCommand.execute();

        return playlistInserted.getId();
    }

    private Playlist getYoutubePlaylistObject() {
        PlaylistSnippet playlistSnippet = new PlaylistSnippet();
        playlistSnippet.setTitle(spotifyPlaylist.getName());
        PlaylistStatus playlistStatus = new PlaylistStatus();
        playlistStatus.setPrivacyStatus("private");
        Playlist youTubePlaylist = new Playlist();
        youTubePlaylist.setSnippet(playlistSnippet);
        youTubePlaylist.setStatus(playlistStatus);

        return youTubePlaylist;
    }

    public void postPlaylist() {
        youtube = new YouTube.Builder(
                Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, request -> {})
                .setApplicationName("SpotifyToYoutube").build();
        List<Item> items = spotifyPlaylist.getItems();

        for (Item item : items) {
            try {
                SearchResult result = getSearchResult(item);
                addToYoutube(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SearchResult getSearchResult(Item item) throws IOException {
        String queryTerm = getQueryTerm(item);
        System.out.println("> + " + queryTerm);
        YouTube.Search.List search = getSearchList(queryTerm);
        SearchListResponse searchResponse = search.execute();

        return searchResponse.getItems().get(0);
    }

    private String getQueryTerm(Item item) {
        return item.getTrack().getArtist(0).getName() +
                " " + item.getTrack().getName();
    }

    private YouTube.Search.List getSearchList(String queryTerm)
            throws IOException {
        YouTube.Search.List search = youtube.search()
                .list(Collections.singletonList("id,snippet"));
        search.setKey(key);
        search.setQ(queryTerm);
        search.setType(Collections.singletonList("video"));
        search.setFields("items(id)");
        search.setMaxResults(1L);

        return search;
    }

    private void addToYoutube(SearchResult searchResult) throws IOException {
        String id = searchResult.getId().getVideoId();
        youtubePlaylistItemDAO.insertPlaylistItem(playlistId, id);
    }
}
