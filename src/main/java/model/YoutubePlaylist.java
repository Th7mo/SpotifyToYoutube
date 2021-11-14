package model;

import java.util.ArrayList;
import java.util.List;

public class YoutubePlaylist {

	private List<String> trackIds = new ArrayList<>();

	public YoutubePlaylist() {}

	public List<String> getTrackIds() {
		return trackIds;
	}

	public void setTrackIds(List<String> trackIds) {
		this.trackIds = trackIds;
	}

	public void join(YoutubePlaylist youtubePlaylist) {
		trackIds.addAll(youtubePlaylist.getTrackIds());
	}
}
