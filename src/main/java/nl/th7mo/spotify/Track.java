package nl.th7mo.spotify;

public class Track {

    private String name;
    private Album album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist(int index) {
        return album.getArtist(index);
    }
}
