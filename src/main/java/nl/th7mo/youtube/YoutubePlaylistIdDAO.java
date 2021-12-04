/*
    Keep it simple - Th7mo
*/

package nl.th7mo.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import nl.th7mo.spotify.playlist.Item;

import java.io.IOException;
import java.util.Collections;

public class YoutubePlaylistIdDAO {

    private final String key;
    private final YouTube youtube;

    public YoutubePlaylistIdDAO(String key, YouTube youtube) {
        this.key = key;
        this.youtube = youtube;
    }

    public SearchResult getSearchResult(Item item) throws IOException {
        String queryTerm = getQueryTerm(item);
        System.out.println("> + " + queryTerm);
        YouTube.Search.List search = getSearchList(queryTerm);
        SearchListResponse searchResponse = search.execute();

        return searchResponse.getItems().get(0);
    }

    private String getQueryTerm(Item item) {
        return item.getTrack().getArtist(0).getName() +
                " " + item.getTrack().getName();
    }

    private YouTube.Search.List getSearchList(String queryTerm)
            throws IOException {
        YouTube.Search.List search = youtube
                .search()
                .list(Collections.singletonList("id,snippet"));
        search.setKey(key);
        search.setQ(queryTerm);
        search.setType(Collections.singletonList("video"));
        search.setFields("items(id)");
        search.setMaxResults(1L);

        return search;
    }
}
