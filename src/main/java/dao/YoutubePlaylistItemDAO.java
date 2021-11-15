package dao;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;

import java.io.IOException;
import java.util.Collections;

public class YoutubePlaylistItemDAO {

	private YouTube youtube;

	public void insertPlaylistItem(String playlistId, String videoId) throws IOException {
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
		playlistItemsInsertCommand.execute();
	}

	public void setYoutube(YouTube youtube) {
		this.youtube = youtube;
	}
}
