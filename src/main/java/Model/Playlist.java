package Model;

import java.util.List;

public interface Playlist {

	List<Item> getItems();
	void setItems(List<Item> items);
	void join(Playlist playlist);
	boolean isEmpty();
}
