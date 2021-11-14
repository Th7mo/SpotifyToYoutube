package DAO;

import Model.Item;
import Model.SpotifyPlaylist;
import Model.YoutubeAuthorizationOptions;
import Model.YoutubePlaylist;
import Util.Authorisation;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YoutubePlaylistDAO {

	private YouTube youtube;
	private YoutubePlaylist playlist;
	private SpotifyPlaylist spotifyPlaylist;

	public void postPlaylist(YoutubePlaylist playlist, SpotifyPlaylist spotifyPlaylist) {
		this.playlist = playlist;
		this.spotifyPlaylist = spotifyPlaylist;

		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

		try {
			Credential credential = Authorisation.authorize(scopes, "playlistupdates");
			youtube = new YouTube.Builder(
					Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, credential)
					.setApplicationName("SpotifyToYoutube").build();

			String playlistId = insertPlaylist();

			for (String id : playlist.getTrackIds()) {
				insertPlaylistItem(playlistId, id);
			}

		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: " +
					e.getDetails().getCode() + " : " +
					e.getDetails().getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String insertPlaylist() throws IOException {
		PlaylistSnippet playlistSnippet = new PlaylistSnippet();
		playlistSnippet.setTitle("Copy of Spotify playlist");
		PlaylistStatus playlistStatus = new PlaylistStatus();
		playlistStatus.setPrivacyStatus("private");

		Playlist youTubePlaylist = new com.google.api.services.youtube.model.Playlist();
		youTubePlaylist.setSnippet(playlistSnippet);
		youTubePlaylist.setStatus(playlistStatus);

		// Call the API to insert the new playlist. In the API call, the first
		// argument identifies the resource parts that the API response should
		// contain, and the second argument is the playlist being inserted.
		YouTube.Playlists.Insert playlistInsertCommand =
				youtube.playlists().insert(Collections.singletonList("snippet,status"), youTubePlaylist);
		Playlist playlistInserted = playlistInsertCommand.execute();

		return playlistInserted.getId();
	}

	private String insertPlaylistItem(String playlistId, String videoId) throws IOException {
		ResourceId resourceId = new ResourceId();
		resourceId.setKind("youtube#video");
		resourceId.setVideoId(videoId);

		PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
		playlistItemSnippet.setPlaylistId(playlistId);
		playlistItemSnippet.setResourceId(resourceId);

		PlaylistItem playlistItem = new PlaylistItem();
		playlistItem.setSnippet(playlistItemSnippet);

		YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtube.playlistItems()
				.insert(Collections.singletonList("snippet,contentDetails"), playlistItem);
		PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();

		return returnedPlaylistItem.getId();
	}

	public YoutubePlaylist getPlaylist(SpotifyPlaylist spotifyPlaylist) {
		YoutubePlaylist youtubePlaylist = new YoutubePlaylist();

		for (Item item : spotifyPlaylist.getItems()) {
			try {
				youtube = new YouTube.Builder(Authorisation.HTTP_TRANSPORT, Authorisation.JSON_FACTORY, request -> {})
						.setApplicationName("SpotifyToYoutube").build();

				String queryTerm = item.getTrack().getArtist(0).getName() + " " + item.getTrack().getName();

				YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));

				String apiKey = YoutubeAuthorizationOptions.API_KEY;
				search.setKey(apiKey);
				search.setQ(queryTerm);

				search.setType(Collections.singletonList("video"));

				search.setFields("items(id)");
				search.setMaxResults(1L);

				SearchListResponse searchResponse = search.execute();
				SearchResult searchResult = searchResponse.getItems().get(0);

				YoutubePlaylist partPlaylist = new YoutubePlaylist();
				List<String> id = new ArrayList<>();
				id.add(searchResult.getId().getVideoId());
				partPlaylist.setTrackIds(id);
				youtubePlaylist.join(partPlaylist);
			} catch (GoogleJsonResponseException e) {
				System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
						+ e.getDetails().getMessage());
			} catch (IOException e) {
				System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		return youtubePlaylist;
	}
}
