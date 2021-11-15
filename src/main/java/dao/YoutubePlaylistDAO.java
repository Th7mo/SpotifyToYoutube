package dao;

import model.Item;
import model.SpotifyPlaylist;
import model.YoutubeAuthorization;
import model.YoutubePlaylist;
import util.Authorisation;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YoutubePlaylistDAO {

	private YouTube youtube;
	private YoutubePlaylist youtubeIds;
	private SpotifyPlaylist spotifyPlaylist;
	private YoutubePlaylistItemDAO youtubePlaylistItemDAO;

	public void postPlaylist(YoutubePlaylist playlist, SpotifyPlaylist spotifyPlaylist) {
		this.youtubeIds = playlist;
		this.spotifyPlaylist = spotifyPlaylist;
		this.youtubePlaylistItemDAO = new YoutubePlaylistItemDAO();

		try {
			setYoutube();
			youtubePlaylistItemDAO.setYoutube(youtube);
			String playlistId = insertPlaylist();

			for (String id : playlist.getTrackIds()) {
				youtubePlaylistItemDAO.insertPlaylistItem(playlistId, id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private YouTube setYoutube() throws IOException {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
		Credential credential = Authorisation.authorize(scopes, "playlistupdates");
		return new YouTube.Builder(
				Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, credential)
				.setApplicationName("SpotifyToYoutube").build();
	}

	private String insertPlaylist() throws IOException {
		PlaylistSnippet playlistSnippet = new PlaylistSnippet();
		playlistSnippet.setTitle("Copy of Spotify playlist");
		PlaylistStatus playlistStatus = new PlaylistStatus();
		playlistStatus.setPrivacyStatus("private");

		Playlist youTubePlaylist = new Playlist();
		youTubePlaylist.setSnippet(playlistSnippet);
		youTubePlaylist.setStatus(playlistStatus);
		YouTube.Playlists.Insert playlistInsertCommand =
				youtube.playlists().insert(Collections.singletonList("snippet,status"), youTubePlaylist);
		Playlist playlistInserted = playlistInsertCommand.execute();

		return playlistInserted.getId();
	}

	public YoutubePlaylist getPlaylist(SpotifyPlaylist spotifyPlaylist) {
		youtube = new YouTube.Builder(
				Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, request -> {})
				.setApplicationName("SpotifyToYoutube").build();
		List<Item> items = spotifyPlaylist.getItems();

		for (Item item : items) {
			try {
				String queryTerm = getQueryTerm(item);
				YouTube.Search.List search = getSearchList(queryTerm);
				SearchListResponse searchResponse = search.execute();
				SearchResult searchResult = searchResponse.getItems().get(0);
				addToYoutubeIds(searchResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return youtubeIds;
	}

	private YouTube.Search.List getSearchList(String queryTerm)
			throws IOException {
		YouTube.Search.List search = youtube.search()
				.list(Collections.singletonList("id,snippet"));
		search.setKey(YoutubeAuthorization.API_KEY);
		search.setQ(queryTerm);
		search.setType(Collections.singletonList("video"));
		search.setFields("items(id)");
		search.setMaxResults(1L);

		return search;
	}

	private String getQueryTerm(Item item) {
		return item.getTrack().getArtist(0).getName() +
				" " + item.getTrack().getName();
	}

	private void addToYoutubeIds(SearchResult searchResult) {
		YoutubePlaylist partPlaylist = new YoutubePlaylist();
		List<String> id = new ArrayList<>();
		id.add(searchResult.getId().getVideoId());
		partPlaylist.setTrackIds(id);
		youtubeIds.join(partPlaylist);
	}
}
