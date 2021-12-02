package nl.th7mo.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;

import java.io.IOException;
import java.util.Collections;

public class YoutubePlaylistItemDAO {

    private YouTube youtube;

    public void insertPlaylistItem(String playlistId, String videoId) throws IOException {
        PlaylistItemSnippet playlistItemSnippet = getPlaylistItemSnippet(playlistId, videoId);
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippet);
        YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtube.playlistItems()
                .insert(Collections.singletonList("snippet,contentDetails"), playlistItem);
        playlistItemsInsertCommand.execute();
    }

    private ResourceId getResourceId(String videoId) {
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);

        return resourceId;
    }

    private PlaylistItemSnippet getPlaylistItemSnippet(String playlistId,
                                                       String videoId) {
        ResourceId resourceId = getResourceId(videoId);
        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);

        return playlistItemSnippet;
    }

    public void setYoutube(YouTube youtube) {
        this.youtube = youtube;
    }
}
