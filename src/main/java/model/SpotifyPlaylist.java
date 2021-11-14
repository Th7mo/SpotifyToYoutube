package model;

import java.util.ArrayList;
import java.util.List;

public class SpotifyPlaylist {

	private List<Item> items = new ArrayList<>();
	private String name;

	public SpotifyPlaylist() {}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void join(SpotifyPlaylist playlist) {
		items.addAll(playlist.getItems());
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int size() {
		return items.size();
	}

	public String getTitle() {
		return name;
	}

	public void setTitle(String title) {
		this.name = title;
	}
}
