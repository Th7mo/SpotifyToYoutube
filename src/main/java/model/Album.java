package model;

import java.util.List;

public class Album {

    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Artist getArtist(int index) {
        return artists.get(index);
    }
}
