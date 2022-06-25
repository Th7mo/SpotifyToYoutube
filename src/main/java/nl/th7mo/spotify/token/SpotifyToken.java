package nl.th7mo.spotify.token;

import com.google.gson.annotations.SerializedName;

public class SpotifyToken {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("expires_in")
    public int expiresIn;
}
