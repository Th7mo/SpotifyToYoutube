/*
    Keep it simple - Th7mo
*/

package nl.th7mo.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import nl.th7mo.spotify.playlist.SpotifyPlaylist;

public class YoutubeBuilder {

    public static YouTube buildYoutube(Credential credential) {
        return new YouTube.Builder(
                Authorisation.HTTP_TRANSPORT,
                Authorisation.JSON_FACTORY,
                credential
        )
        .setApplicationName("SpotifyToYoutube")
        .build();
    }

    public static YouTube buildYoutube() {
        return new YouTube.Builder(
                Authorisation.HTTP_TRANSPORT,
                Authorisation.JSON_FACTORY,
                request -> {}
        )
        .setApplicationName("SpotifyToYoutube")
        .build();
    }

    public static Playlist getYoutubePlaylistObject(SpotifyPlaylist spotifyPlaylist) {
        PlaylistSnippet playlistSnippet = new PlaylistSnippet();
        playlistSnippet.setTitle(spotifyPlaylist.getName());
        PlaylistStatus playlistStatus = new PlaylistStatus();
        playlistStatus.setPrivacyStatus("private");
        Playlist youTubePlaylist = new Playlist();
        youTubePlaylist.setSnippet(playlistSnippet);
        youTubePlaylist.setStatus(playlistStatus);

        return youTubePlaylist;
    }
}
