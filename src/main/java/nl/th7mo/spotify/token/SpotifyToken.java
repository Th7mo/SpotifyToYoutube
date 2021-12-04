package nl.th7mo.spotify.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyToken {

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("expires_in")
    public int expiresIn;
}
