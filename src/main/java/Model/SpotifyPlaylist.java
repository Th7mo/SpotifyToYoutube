package Model;

import java.util.ArrayList;
import java.util.List;

public class SpotifyPlaylist implements Playlist {

	private List<Item> items = new ArrayList<>();

	public SpotifyPlaylist() {}

	@Override
	public List<Item> getItems() {
		return items;
	}

	@Override
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public void join(Playlist playlist) {
		items.addAll(playlist.getItems());
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public int size() {
		return items.size();
	}
}
