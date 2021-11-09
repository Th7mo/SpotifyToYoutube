package Model;

import java.util.List;

public class SpotifyPlaylist implements Playlist {

	private List<Item> items;

	public SpotifyPlaylist() {}

	@Override
	public List<Item> getItems() {
		return items;
	}

	@Override
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
