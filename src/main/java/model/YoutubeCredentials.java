package model;

public class YoutubeCredentials {

    private YoutubeOath installed;
    private String apiKey;

    public YoutubeOath getInstalled() {
        return installed;
    }

    public void setInstalled(YoutubeOath installed) {
        this.installed = installed;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
